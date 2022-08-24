#include <iostream>
#include <glm/glm.hpp>
#include <SDL.h>
#include "SDLauxiliary.h"
#include "TestModel.h"
#include <algorithm>

using glm::vec3;
using glm::ivec2;
using glm::vec2;
using glm::mat3;

struct Pixel {
	int x;
	int y;
	float zinv;
	vec3 pos3d;
};

struct Vertex
{
	vec3 position;
};

const int SCREEN_WIDTH = 500;
const int SCREEN_HEIGHT = 500;
float PI = acos(-1);
SDL_Surface* screen;
int t;
float focalLength = SCREEN_HEIGHT;
mat3 R = mat3(vec3(1.0f, 0.0f, 0.0f), vec3(0.0f, 1.0f, 0.0f), vec3(0.0f, 0.0f, 1.0f));
vec3 right(R[0][0], R[0][1], R[0][2]);
vec3 down(R[1][0], R[1][1], R[1][2]);
vec3 forward(R[2][0], R[2][1], R[2][2]);
float yaw = 0;
vec3 cameraPos(0, 0, -3.001);
vec3 currentColor;
std::vector<Triangle> triangles;
float depthBuffer[SCREEN_HEIGHT][SCREEN_WIDTH];
vec3 lightPos(0, -0.5, -0.7);
vec3 lightPower = 14.1f * vec3(1,1,1);
vec3 indirectLightPowerPerArea = 0.5f*vec3(1, 1, 1);
vec3 currentNormal;
vec3 currentReflectance;

void computePolygonRows(const std::vector<Pixel>&, std::vector<Pixel>&, std::vector<Pixel>&);
void draw();
void drawLineSDL(SDL_Surface*, Pixel, Pixel, vec3);
void drawPolygon(const std::vector<Vertex>&);
//void drawPolygonEdges(const std::vector<vec3>& vertices);
void drawPolygonRows(const std::vector<Pixel>&, const std::vector<Pixel>&);
void interpolate(Pixel, Pixel, std::vector<Pixel>&);
void pixelShader(const Pixel&);
void rotateCamera();
void update();
void vertexShader(const Vertex&, Pixel&);
vec3 directLight(const Vertex&);


// 625 ms for figure 4.
// 890 ms for figure 4 with depth buffer.
// 1200 ms for figure 6.
int main(int argc, char* argv[]) {
	LoadTestModel(triangles);
	screen = InitializeSDL(SCREEN_WIDTH, SCREEN_HEIGHT);
	t = SDL_GetTicks();	// Set start value for timer.

	while (NoQuitMessageSDL())
	{
		update();
		draw();
	}

	SDL_SaveBMP(screen, "screenshot.bmp");
	return 0;
}

void update() {
	// Compute frame time:
	int t2 = SDL_GetTicks();
	float dt = float(t2 - t);
	t = t2;
	std::cout << "Render time: " << dt << " ms." << std::endl;

	Uint8* keystate = SDL_GetKeyState(0);

	if (keystate[SDLK_UP]) {
		cameraPos += forward;
	}
	if (keystate[SDLK_DOWN]) {
		cameraPos -= forward;
	}
	if (keystate[SDLK_LEFT]) {
		yaw -= 0.03f;
		rotateCamera();
		cameraPos -= right;
	}
	if (keystate[SDLK_RIGHT]) {
		yaw += 0.03f;
		rotateCamera();
		cameraPos += right;
	}
	if (keystate[SDLK_w]) {
		lightPos += forward;
	}
	if (keystate[SDLK_a]) {
		lightPos -= right;
	}
	if (keystate[SDLK_s]) {
		lightPos -= forward;
	}
	if (keystate[SDLK_d]) {
		lightPos += right;
	}
	if (keystate[SDLK_q]) {
		lightPos -= down;
	}
	if (keystate[SDLK_e]) {
		lightPos += down;
	}
}

void draw() { 

	for (int y = 0; y < SCREEN_HEIGHT; ++y) {
		for (int x = 0; x < SCREEN_WIDTH; ++x) {
			depthBuffer[y][x] = 0;
		}
	}

	SDL_FillRect(screen, 0, 0);
	if (SDL_MUSTLOCK(screen))
		SDL_LockSurface(screen);
	for (int i = 0; i < triangles.size(); ++i)
	{
		currentColor = triangles[i].color;
		std::vector<Vertex> vertices(3);
		vertices[0].position = triangles[i].v0;
		vertices[1].position = triangles[i].v1;
		vertices[2].position = triangles[i].v2;
		currentNormal = triangles[i].normal;
		currentReflectance = triangles[i].color;

		drawPolygon(vertices);
	}
	if (SDL_MUSTLOCK(screen))
		SDL_UnlockSurface(screen);
	SDL_UpdateRect(screen, 0, 0, 0, 0);
}

void drawPolygon(const std::vector<Vertex>& vertices)
{
	int V = vertices.size();
	// Transform each vertex from 3D world position to 2D image position:
	std::vector<Pixel> vertexPixels(V);
	for (int i = 0; i < V; ++i)
		vertexShader(vertices[i], vertexPixels[i]);
	std::vector<Pixel> leftPixels;
	std::vector<Pixel> rightPixels;
	computePolygonRows(vertexPixels, leftPixels, rightPixels);
	drawPolygonRows(leftPixels, rightPixels);
}

void drawPolygonRows(const std::vector<Pixel>& leftPixels, const std::vector<Pixel>& rightPixels) {
	for (int i = 0; i < leftPixels.size(); ++i) {
		drawLineSDL(screen, leftPixels[i], rightPixels[i], currentColor);
	}
}

void drawLineSDL(SDL_Surface* surface, Pixel a, Pixel b, vec3 color) {
	Pixel delta;
	delta.x = glm::abs(a.x - b.x);
	delta.y = glm::abs(a.y - b.y);
	int pixels = glm::max(delta.x, delta.y) + 1;

	std::vector<Pixel> line(pixels);
	interpolate(a, b, line);
	for (int i = 0; i < line.size(); ++i) {
			pixelShader(line[i]);
	}
}

void interpolate(Pixel a, Pixel b, std::vector<Pixel>& result) {
	int N = result.size();
	float xStep = (b.x - a.x)/ (float) std::max(N-1,1);
	float yStep = (b.y - a.y)/ (float) std::max(N-1,1);
	float zStep = (b.zinv - a.zinv)/ float(std::max(N-1,1));

	// Convert in order to interpolate correctly
	b.pos3d *= b.zinv;
	a.pos3d *= a.zinv;
	vec3 pos3dStep = (b.pos3d - a.pos3d)/ float(std::max(N-1, 1));

	for( int i=0; i<N; ++i ) 
	{ 
		result[i].x = a.x + xStep*i;
		result[i].y = a.y + yStep*i;
		result[i].zinv = a.zinv + zStep*i;
		result[i].pos3d = a.pos3d + pos3dStep*(float)i;
		// Convert back
		result[i].pos3d /= result[i].zinv;
	} 
}

void rotateCamera() {
	vec3 a(cos(yaw), 0, -sin(yaw));
	vec3 b(0, 1, 0);
	vec3 c(sin(yaw), 0, cos(yaw));
	R = mat3(a, b, c);
	right = vec3(R[0][0], R[0][1], R[0][2]);
	down = vec3(R[1][0], R[1][1], R[1][2]);
	forward = vec3(R[2][0], R[2][1], R[2][2]);
}

void computePolygonRows(const std::vector<Pixel>& vertexPixels, std::vector<Pixel>& leftPixels, std::vector<Pixel>& rightPixels) {
	int minY = std::numeric_limits<int>::max();
	int maxY = -std::numeric_limits<int>::max();

	// 1. Find std::max and min y-value of the polygon 
	// and compute the number of rows it occupies. 
	for (int i = 0; i< vertexPixels.size(); ++i) {
		minY = std::min(vertexPixels[i].y, minY);
		maxY = std::max(vertexPixels[i].y, maxY);
	}
	int rows = maxY - minY + 1;

	// 2. Resize leftPixels and rightPixels 
	// so that they have an element for each row. 
	leftPixels.resize(rows);
	rightPixels.resize(rows);

	// 3. Initialize the x-coordinates in leftPixels 
	// to some really large value and the x-coordinates 
	// in rightPixels to some really small value.
	for (int i = 0; i<rows; ++i) {
		leftPixels[i].x = +std::numeric_limits<int>::max();
		rightPixels[i].x = -std::numeric_limits<int>::max();
	}

	// 4. Loop through all edges of the polygon and use 
	// linear interpolation to find the x-coordinate for 
	// each row it occupies. Update the corresponding 
	// values in rightPixels and leftPixels.
	for (int i = 0; i < vertexPixels.size(); ++i) {

		int j = (i + 1) % vertexPixels.size(); // The next vertex
		int pixels = abs(vertexPixels[i].y - vertexPixels[j].y) + 1;
		std::vector<Pixel> line(pixels);

		interpolate(vertexPixels[i], vertexPixels[j], line);
		for (int k = 0; k < line.size(); ++k) {
			int rowNum = line[k].y - minY;
			if (leftPixels[rowNum].x > line[k].x) {
				leftPixels[rowNum] = line[k];
			}
			if (rightPixels[rowNum].x < line[k].x) {
				rightPixels[rowNum] = line[k];

			}

		}
	}

}

void vertexShader(const Vertex& v, Pixel& p) {

	// Translate the points according to equations 3, 4, and 5
	vec3 p_prime = (v.position - cameraPos) * R;
	p.zinv = 1 / p_prime.z;
	p.x = focalLength * p_prime.x / p_prime.z + SCREEN_WIDTH / 2.0f;
	p.y = focalLength * p_prime.y / p_prime.z + SCREEN_HEIGHT / 2.0f;
	p.pos3d = v.position;

}

vec3 directLight(const Pixel& p) {
	vec3 n_hat = currentNormal;
	vec3 r_hat = glm::normalize(lightPos - p.pos3d);
	float r = glm::length(lightPos - p.pos3d);

	return lightPower * std::max(glm::dot(r_hat, n_hat), 0.0f) / (4.0f * PI * pow(r, 2.0f));
}


void pixelShader(const Pixel& p) {
	int x = p.x;
	int y = p.y;
	if (p.zinv > depthBuffer[y][x])
	{
		depthBuffer[y][x] = p.zinv;
		PutPixelSDL(screen, x, y, currentReflectance * (directLight(p) + indirectLightPowerPerArea));
	}
}


/*
void drawPolygonEdges(const std::vector<vec3>& vertices) {
int V = vertices.size();
// Transform each vertex from 3D world position to 2D image position:
std::vector<Pixel> projectedVertices(V);
for (int i = 0; i<V; ++i)
{
vertexShader(vertices[i], projectedVertices[i]);
}
// Loop over all vertices and draw the edge from it to the next vertex:
for (int i = 0; i<V; ++i)
{
int j = (i + 1) % V; // The next vertex
vec3 color(1, 1, 1);
drawLineSDL(screen, projectedVertices[i], projectedVertices[j],	color);
}
}
*/

/*
void interpolate(ivec2 a, ivec2 b, std::vector<ivec2>& result) {
int N = result.size();
vec2 step = vec2(b - a) / float(std::max(N - 1, 1));
vec2 current(a);
for (int i = 0; i<N; ++i)
{
result[i] = current;
current += step;
}
}
*/