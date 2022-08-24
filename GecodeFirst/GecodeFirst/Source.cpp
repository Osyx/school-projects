#include <gecode/gist.hh>
#include <gecode/int.hh>
#include <gecode/search.hh>
#include <gecode/minimodel.hh>

using namespace Gecode;

class SendMoreMoney : public Space {
	protected:
		IntVarArray l; // Digits for the letters
	public:
		// Constructor for script
		SendMoreMoney(void) : l(*this, 8, 0, 9) {
			IntVar s(l[0]), e(l[1]), n(l[2]), d(l[3]),
				m(l[4]), o(l[5]), r(l[6]), y(l[7]);
			// No leading zeros (IRT: integer relation type)
			rel(*this, s, IRT_NQ, 0);
			rel(*this, m, IRT_NQ, 0);
			// All letters must take distinct digits
			distinct(*this, l);
			// The linear equation must hold
			rel(*this, 1000 * s + 100 * e + 10 * n + d
				+ 1000 * m + 100 * o + 10 * r + e
				== 10000 * m + 1000 * o + 100 * n + 10 * e + y);
			// Branch over the letters
			branch(*this, l, INT_VAR_SIZE_MIN(), INT_VAL_MIN());
		}
		// Constructor for cloning
		SendMoreMoney(bool share, SendMoreMoney& s) : Space(share, s) {
			l.update(*this, share, s.l);
		}
		// Perform copying during cloning
		virtual Space* copy(bool share) {
			return new SendMoreMoney(share, *this);
		}
		// Print solution
		void print(void) {
			std::cout << l << std::endl;
		}
};

void useGist();
void useSearch();

int main(int argc, char* argv[]) {
	useSearch();
	useGist();
}

void useGist() {
	SendMoreMoney* m = new SendMoreMoney;
	Gist::dfs(m);
	delete m;
}

void useSearch() {
	SendMoreMoney* m = new SendMoreMoney;
	DFS<SendMoreMoney> e(m);
	delete m;
	while (SendMoreMoney* s = e.next()) {
		s->print(); delete s;
	}
}