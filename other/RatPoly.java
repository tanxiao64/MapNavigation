package hw4;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * <b>RatPoly</b> represents an immutable single-variate polynomial expression.
 * RatPolys are sums of RatTerms with non-negative exponents.
 * <p>
 *
 * Examples of RatPolys include "0", "x-10", and "x^3-2*x^2+5/3*x+3", and "NaN".
 */
// See RatNum's documentation for a definition of "immutable".
public final class RatPoly {

  /** Holds all the RatTerms in this RatPoly */
  private final List<RatTerm> terms;

  // Definitions:
  // For a RatPoly p, let C(p,i) be "p.terms.get(i).getCoeff()" and
  // E(p,i) be "p.terms.get(i).getExpt()"
  // length(p) be "p.terms.size()"
  // (These are helper functions that will make it easier for us
  // to write the remainder of the specifications. They are not
  // executable code; they just represent complex expressions in a
  // concise manner, so that we can stress the important parts of
  // other expressions in the spec rather than get bogged down in
  // the details of how we extract the coefficient for the 2nd term
  // or the exponent for the 5th term. So when you see C(p,i),
  // think "coefficient for the ith term in p".)
  //
  // Abstraction Function:
  // RatPoly, p, represents the polynomial equal to the sum of the
  // RatTerms contained in 'terms':
  // sum (0 <= i < length(p)): p.terms.get(i)
  // If there are no terms, then the RatPoly represents the zero
  // polynomial.
  //
  // Representation Invariant for every RatPoly p:
  // terms != null &&
  // forall i such that (0 <= i < length(p)), C(p,i) != 0 &&
  // forall i such that (0 <= i < length(p)), E(p,i) >= 0 &&
  // forall i such that (0 <= i < length(p) - 1), E(p,i) > E(p, i+1)
  // In other words:
  // * The terms field always points to some usable object.
  // * No term in a RatPoly has a zero coefficient.
  // * No term in a RatPoly has a negative exponent.
  // * The terms in a RatPoly are sorted in descending exponent order.
  // (It is implied that 'terms' does not contain any null elements by the
  // above
  // invariant.)

  /** A constant holding a Not-a-Number (NaN) value of type RatPoly */
  public static final RatPoly NaN = new RatPoly(RatTerm.NaN);

  /** A constant holding a zero value of type RatPoly */
  public static final RatPoly ZERO = new RatPoly();

  /**
   * @effects Constructs a new Poly, "0".
   */
  public RatPoly() {
    terms = new ArrayList<RatTerm>();
    checkRep();
  }

  /**
   * @param rt The single term which the new RatPoly equals.
   * @requires rt.getExpt() >= 0
   * @effects Constructs a new Poly equal to "rt". If rt.getCoeff() is zero,
   *          constructs a "0" polynomial.
   */
  public RatPoly(RatTerm rt) {
    // TODO: Fill in this method, then remove the RuntimeException
	  ArrayList<RatTerm> listTemp = new ArrayList<RatTerm>();
	  if (rt.getCoeff().equals(RatNum.ZERO)) {	// Create 0 polynomial
		  terms = new ArrayList<RatTerm>();
	  } else {
		  listTemp.add(rt);	// Create a new Poly equal to "rt"
		  terms = listTemp;
	  }
	  checkRep();
  }

  /**
   * @param c The constant in the term which the new RatPoly equals.
   * @param e The exponent in the term which the new RatPoly equals.
   * @requires e >= 0
   * @effects Constructs a new Poly equal to "c*x^e". If c is zero, constructs
   *          a "0" polynomial.
   */
  public RatPoly(int c, int e) {
    // TODO: Fill in this method, then remove the RuntimeException
	  RatNum coef = new RatNum(c);
	  RatTerm rt = new RatTerm(coef,e);
	  ArrayList<RatTerm> listTemp = new ArrayList<RatTerm>();
	  if (rt.getCoeff().equals(RatNum.ZERO)) {	// Create 0 polynomial
		  terms = new ArrayList<RatTerm>();
	  } else {
		  listTemp.add(rt);	// Create a new Poly equal to "c*x^e"
		  terms = listTemp;
	  }
	  checkRep();
  }

  /**
   * @param rt A list of terms to be contained in the new RatPoly.
   * @requires 'rt' satisfies clauses given in rep. invariant
   * @effects Constructs a new Poly using 'rt' as part of the representation.
   *          The method does not make a copy of 'rt'.
   */
  private RatPoly(List<RatTerm> rt) {
    terms = rt;
    // The spec tells us that we don't need to make a copy of 'rt'
    checkRep();
  }

  /**
   * Returns the degree of this RatPoly.
   *
   * @requires !this.isNaN()
   * @return the largest exponent with a non-zero coefficient, or 0 if this is
   *         "0".
   */
  public int degree() {
    // TODO: Fill in this method, then remove the RuntimeException
	 if (this.equals(ZERO)) {
		 return 0;
	 }
	 return this.terms.get(0).getExpt();
  }

  /**
   * Gets the RatTerm associated with degree 'deg'
   *
   * @param deg The degree for which to find the corresponding RatTerm.
   * @requires !this.isNaN()
   * @return the RatTerm of degree 'deg'. If there is no term of degree 'deg'
   *         in this poly, then returns the zero RatTerm.
   */
  public RatTerm getTerm(int deg) {
    // TODO: Fill in this method, then remove the RuntimeException
	int pos = -1;	// store the position of the RatTerm of degree 'deg' in the terms, -1 means no such position
    for (int i = 0; i < this.terms.size(); i++) {
    	if (this.terms.get(i).getExpt() == deg) {
    		pos = i;
    	}
    }
    if (pos == -1) {
    	return RatTerm.ZERO;
    } else {
    	return this.terms.get(pos);
    }
  }

  /**
   * Returns true if this RatPoly is not-a-number.
   *
   * @return true if and only if this has some coefficient = "NaN".
   */
  public boolean isNaN() {
    // TODO: Fill in this method, then remove the RuntimeException
	for (int i = 0; i < this.terms.size(); i++) {
		if (this.terms.get(i).getCoeff().isNaN()) {
			return true;
		}
	}
    return false;
  }

  /**
   * Scales coefficients within 'lst' by 'scalar' (helper procedure).
   *
   * @param lst The RatTerms to be scaled.
   * @param scalar the value by which to scale coefficients in lst.
   * @requires lst, scalar != null
   * @modifies lst
   * @effects Forall i s.t. 0 <= i < lst.size(), if lst.get(i) = (C . E) then
   *          lst_post.get(i) = (C*scalar . E)
   * @see hw4.RatTerm regarding (C . E) notation
   */
  private static void scaleCoeff(List<RatTerm> lst, RatNum scalar) {
    // TODO: Fill in this method, then remove the RuntimeException
    for (int i = 0; i < lst.size(); i++) {
    	RatTerm term = new RatTerm(scalar, 0);
    	lst.set(i, lst.get(i).mul(term)); 
    }
  }

  /**
   * Increments exponents within 'lst' by 'degree' (helper procedure).
   *
   * @param lst The RatTerms whose exponents are to be incremented.
   * @param degree the value by which to increment exponents in lst.
   * @requires lst != null
   * @modifies lst
   * @effects Forall i s.t. 0 <= i < lst.size(), if (C . E) = lst.get(i) then
   *          lst_post.get(i) = (C . E+degree)
   * @see hw4.RatTerm regarding (C . E) notation
   */
  private static void incremExpt(List<RatTerm> lst, int degree) {
    // TODO: Fill in this method, then remove the RuntimeException
	  RatNum ratOne = new RatNum(1);
	  for (int i = 0; i < lst.size(); i++) {
		  RatTerm term = new RatTerm(ratOne, degree);
		  lst.set(i,lst.get(i).mul(term));
	  }
  }

  /**
   * Helper procedure: Inserts a term into a sorted sequence of terms,
   * preserving the sorted nature of the sequence. If a term with the given
   * degree already exists, adds their coefficients.
   *
   * Definitions: Let a "Sorted List<RatTerm>" be a List<RatTerm> V such
   * that [1] V is sorted in descending exponent order && [2] there are no two
   * RatTerms with the same exponent in V && [3] there is no RatTerm in V with
   * a coefficient equal to zero
   *
   * For a Sorted List<RatTerm> V and integer e, let cofind(V, e) be either
   * the coefficient for a RatTerm rt in V whose exponent is e, or zero if
   * there does not exist any such RatTerm in V. (This is like the coeff
   * function of RatPoly.) We will write sorted(lst) to denote that lst is a
   * Sorted List<RatTerm>, as defined above.
   *
   * @param lst The list into which newTerm should be inserted.
   * @param newTerm The term to be inserted into the list.
   * @requires lst != null && sorted(lst)
   * @modifies lst
   * @effects sorted(lst_post) && (cofind(lst_post,newTerm.getExpt()) =
   *          cofind(lst,newTerm.getExpt()) + newTerm.getCoeff())
   */
  private static void sortedInsert(List<RatTerm> lst, RatTerm newTerm) {
    // TODO: Fill in this method, then remove the RuntimeException
	if (lst.size() == 0) {  // newTerm add to a empty list
		lst.add(newTerm);
		return;
	}
    for (int i = 0; i < lst.size(); i++) {
    	if (lst.get(i).getExpt() == newTerm.getExpt()) {	// if two terms have the same exponent
    		lst.set(i, lst.get(i).add(newTerm));
    		if (lst.get(i).isZero() && lst.size() > 1) {
    			lst.remove(i);
    		}
    		return;
    	} else {
    		if (lst.get(i).getExpt() < newTerm.getExpt()) {	// if the term in lst with exponent < newTerm's exponent
    			if (i == 0) {	// if no term in lst have exponent > newTerm's exponent
    				lst.add(0,newTerm);	// add to the front
    				return;
    			}
    			if (lst.get(i-1).getExpt() > newTerm.getExpt()) {	// new term should between lst[i-1] and lst[i]
    				lst.add(i,newTerm);
    				return;
    			}
    		}
    	} 	
    }
    lst.add(lst.size(),newTerm); // if newTerm's exponent is less than all terms in this
  }

  /**
   * Return the additive inverse of this RatPoly.
   *
   * @return a RatPoly equal to "0 - this"; if this.isNaN(), returns some r
   *         such that r.isNaN()
   */
  public RatPoly negate() {
    // TODO: Fill in this method, then remove the RuntimeException
    if (this.isNaN()) {
    	return RatPoly.NaN;
    } else {
    	RatPoly p = new RatPoly(-1,0);
    	return this.mul(p);
    }
  }

  /**
   * Addition operation.
   *
   * @param p The other value to be added.
   * @requires p != null
   * @return a RatPoly, r, such that r = "this + p"; if this.isNaN() or
   *         p.isNaN(), returns some r such that r.isNaN()
   */
  public RatPoly add(RatPoly p) {
    // TODO: Fill in this method, then remove the RuntimeException
	if (this.isNaN() || p.isNaN() ){
		return RatPoly.NaN;
	}
	List<RatTerm> r = new ArrayList<RatTerm>();
	for (int i = 0; i < this.terms.size(); i++) {
		sortedInsert(r,this.terms.get(i));
	}
	for (int i = 0; i < p.terms.size(); i++) {
		sortedInsert(r,p.terms.get(i));
	}
	RatPoly ratP;
	if (r.size() == 1 && r.get(0).isZero()) {
		ratP = new RatPoly(r.get(0));
	} else {
		ratP = new RatPoly(r);
	}
	return ratP;
  }

  /**
   * Subtraction operation.
   *
   * @param p The value to be subtracted.
   * @requires p != null
   * @return a RatPoly, r, such that r = "this - p"; if this.isNaN() or
   *         p.isNaN(), returns some r such that r.isNaN()
   */
  public RatPoly sub(RatPoly p) {
    // TODO: Fill in this method, then remove the RuntimeException
	if (this.isNaN() || p.isNaN() ){
		return RatPoly.NaN;
	}
    return this.add(p.negate());
  }

  /**
   * Multiplication operation.
   *
   * @param p The other value to be multiplied.
   * @requires p != null
   * @return a RatPoly, r, such that r = "this * p"; if this.isNaN() or
   *         p.isNaN(), returns some r such that r.isNaN()
   */
  public RatPoly mul(RatPoly p) {
    // TODO: Fill in this method, then remove the RuntimeException
	if (this.isNaN() || p.isNaN() ){
		return RatPoly.NaN;
	}
	List<RatTerm> r = new ArrayList<RatTerm>();
	RatNum coe;
	int deg;
	for (int i = 0; i < this.terms.size(); i++) {
		for (int j = 0; j < p.terms.size(); j++) {
			coe = this.terms.get(i).getCoeff().mul(p.terms.get(j).getCoeff());
			deg = this.terms.get(i).getExpt() + p.terms.get(j).getExpt();
			sortedInsert(r,new RatTerm(coe,deg));
		}
	}
	RatPoly ratP = new RatPoly(r);
	return ratP;
  }

  /**
   * Division operation (truncating).
   *
   * @param p The divisor.
   * @requires p != null
   * @return a RatPoly, q, such that q = "this / p"; if p = 0 or this.isNaN()
   *         or p.isNaN(), returns some q such that q.isNaN()
   *         <p>
   *
   * Division of polynomials is defined as follows: Given two polynomials u
   * and v, with v != "0", we can divide u by v to obtain a quotient
   * polynomial q and a remainder polynomial r satisfying the condition u = "q *
   * v + r", where the degree of r is strictly less than the degree of v, the
   * degree of q is no greater than the degree of u, and r and q have no
   * negative exponents.
   * <p>
   *
   * For the purposes of this class, the operation "u / v" returns q as
   * defined above.
   * <p>
   *
   * The following are examples of div's behavior: "x^3-2*x+3" / "3*x^2" =
   * "1/3*x" (with r = "-2*x+3"). "x^2+2*x+15 / 2*x^3" = "0" (with r =
   * "x^2+2*x+15"). "x^3+x-1 / x+1 = x^2-x+2 (with r = "-3").
   * <p>
   *
   * Note that this truncating behavior is similar to the behavior of integer
   * division on computers.
   */
  public RatPoly div(RatPoly p) {
    // TODO: Fill in this method, then remove the RuntimeException
    if (p.equals(ZERO) || this.isNaN() || p.isNaN()) {
    	return NaN;
    }
    List<RatTerm> uTemp = new ArrayList<RatTerm>();	// store the remaining terms in each step
	for (int i = 0; i < this.terms.size(); i++) {
		sortedInsert(uTemp,this.terms.get(i));
	}
	
	List<RatTerm> q = new ArrayList<RatTerm>();	// quotient
	int maxE = p.degree(); // get the largest exponent in dividend,  p
	RatTerm maxTerm = p.getTerm(maxE);	//get the term with largest exponent in p
	RatPoly ans = new RatPoly();
	int i = 0;
	while (uTemp.size() > 0){
		if (maxE <= uTemp.get(i).getExpt()) {
			RatNum qcoe;
			qcoe = uTemp.get(i).getCoeff().div(maxTerm.getCoeff());
			if (qcoe.equals(new RatNum(0))) {break;}
			int qdeg = uTemp.get(i).getExpt()-maxTerm.getExpt();
			RatTerm tTerm = new RatTerm(qcoe,qdeg); // t is the quotient
			sortedInsert(q,tTerm); // add t=RatTerm(qcoe,qdeg) to q
			RatPoly tPoly = new RatPoly(tTerm); // convert RatTerm to RatPoly 
			RatPoly tempPoly = p.mul(tPoly.negate());	// p * t
			RatPoly uTempPoly = new RatPoly(uTemp); // convert RatTerm to RatPoly
			ans = uTempPoly.add(tempPoly); // p * t + uTemp
			uTemp.clear();
			for (int j = 0; j < ans.terms.size(); j++){	// copy answer to new uTemp
				sortedInsert(uTemp,ans.terms.get(j));
			}
		} else {
			RatPoly r = new RatPoly();
			r.add(ans);
			break;
		}
	}
	RatPoly ratP = new RatPoly(q);
	return ratP;
  }

  /**
   * Return the derivative of this RatPoly.
   *
   * @return a RatPoly, q, such that q = dy/dx, where this == y. In other
   *         words, q is the derivative of this. If this.isNaN(), then return
   *         some q such that q.isNaN()
   *
   * <p>
   * The derivative of a polynomial is the sum of the derivative of each term.
   */
  public RatPoly differentiate() {
    // TODO: Fill in this method, then remove the RuntimeException
    if (this.isNaN()) {
    	return NaN;
    }
    RatPoly ans = new RatPoly();
    for (int i = 0; i < this.terms.size(); i++) {
    	RatPoly dPoly = new RatPoly(this.terms.get(i).differentiate());
    	ans = ans.add(dPoly);
    }
    return ans;
  }

  /**
   * Returns the antiderivative of this RatPoly.
   *
   * @param integrationConstant The constant of integration to use when
   *  computating the antiderivative.
   * @requires integrationConstant != null
   * @return a RatPoly, q, such that dq/dx = this and the constant of
   *         integration is "integrationConstant" In other words, q is the
   *         antiderivative of this. If this.isNaN() or
   *         integrationConstant.isNaN(), then return some q such that
   *         q.isNaN()
   *
   * <p>
   * The antiderivative of a polynomial is the sum of the antiderivative of
   * each term plus some constant.
   */
  public RatPoly antiDifferentiate(RatNum integrationConstant) {
    // TODO: Fill in this method, then remove the RuntimeException
	if (this.isNaN() || integrationConstant.isNaN()) {
	    return NaN;
	}
	RatPoly ans = new RatPoly();
	for (int i = 0; i < this.terms.size(); i++) {
		RatPoly adPoly = new RatPoly(this.terms.get(i).antiDifferentiate());
	    ans = ans.add(adPoly);
	}
	RatPoly intergPoly = new RatPoly(new RatTerm(integrationConstant,0));
	ans = ans.add(intergPoly);
	return ans;
  }

  /**
   * Returns the integral of this RatPoly, integrated from lowerBound to
   * upperBound.
   *
   * <p>
   * The Fundamental Theorem of Calculus states that the definite integral of
   * f(x) with bounds a to b is F(b) - F(a) where dF/dx = f(x) NOTE: Remember
   * that the lowerBound can be higher than the upperBound.
   *
   * @param lowerBound The lower bound of integration.
   * @param upperBound The upper bound of integration.
   * @return a double that is the definite integral of this with bounds of
   *         integration between lowerBound and upperBound. If this.isNaN(),
   *         or either lowerBound or upperBound is Double.NaN, return
   *         Double.NaN.
   */
  public double integrate(double lowerBound, double upperBound) {
    // TODO: Fill in this method, then remove the RuntimeException
    if (this.isNaN() || lowerBound == Double.NaN || upperBound == Double.NaN) {
    	return Double.NaN;
    }
    RatPoly Fx = new RatPoly();
    Fx = Fx.add(this.antiDifferentiate(new RatNum(0)));
    return Fx.eval(upperBound)-Fx.eval(lowerBound);
  }

  /**
   * Returns the value of this RatPoly, evaluated at d.
   *
   * @param d The value at which to evaluate this polynomial.
   * @return the value of this polynomial when evaluated at 'd'. For example,
   *         "x+2" evaluated at 3 is 5, and "x^2-x" evaluated at 3 is 6. if
   *         (this.isNaN() == true), return Double.NaN
   */
  public double eval(double d) {
    // TODO: Fill in this method, then remove the RuntimeException
	double ans = 0;
	for (int i = 0; i < this.terms.size(); i++) {
		ans = ans + this.terms.get(i).eval(d);
	}
    return ans;
  }

  /**
   * Returns a string representation of this RatPoly.
   *
   * @return A String representation of the expression represented by this,
   *         with the terms sorted in order of degree from highest to lowest.
   *         <p>
   *         There is no whitespace in the returned string.
   *         <p>
   *         If the polynomial is itself zero, the returned string will just
   *         be "0".
   *         <p>
   *         If this.isNaN(), then the returned string will be just "NaN"
   *         <p>
   *         The string for a non-zero, non-NaN poly is in the form
   *         "(-)T(+|-)T(+|-)...", where "(-)" refers to a possible minus
   *         sign, if needed, and "(+|-)" refer to either a plus or minus
   *         sign, as needed. For each term, T takes the form "C*x^E" or "C*x"
   *         where C > 0, UNLESS: (1) the exponent E is zero, in which case T
   *         takes the form "C", or (2) the coefficient C is one, in which
   *         case T takes the form "x^E" or "x". In cases were both (1) and
   *         (2) apply, (1) is used.
   *         <p>
   *         Valid example outputs include "x^17-3/2*x^2+1", "-x+1", "-1/2",
   *         and "0".
   *         <p>
   */
  @Override
  public String toString() {
    if (terms.size() == 0) {
      return "0";
    }
    if (isNaN()) {
      return "NaN";
    }
    StringBuilder output = new StringBuilder();
    boolean isFirst = true;
    for (RatTerm rt : terms) {
      if (isFirst) {
        isFirst = false;
        output.append(rt.toString());
      } else {
        if (rt.getCoeff().isNegative()) {
          output.append(rt.toString());
        } else {
          output.append("+" + rt.toString());
        }
      }
    }
    return output.toString();
  }

  /**
   * Builds a new RatPoly, given a descriptive String.
   *
   * @param ratStr A string of the format described in the @requires clause.
   * @requires 'polyStr' is an instance of a string with no spaces that
   *           expresses a poly in the form defined in the toString() method.
   *           <p>
   *
   * Valid inputs include "0", "x-10", and "x^3-2*x^2+5/3*x+3", and "NaN".
   *
   * @return a RatPoly p such that p.toString() = polyStr
   */
  public static RatPoly valueOf(String polyStr) {

    List<RatTerm> parsedTerms = new ArrayList<RatTerm>();

    // First we decompose the polyStr into its component terms;
    // third arg orders "+" and "-" to be returned as tokens.
    StringTokenizer termStrings = new StringTokenizer(polyStr, "+-", true);

    boolean nextTermIsNegative = false;
    while (termStrings.hasMoreTokens()) {
      String termToken = termStrings.nextToken();

      if (termToken.equals("-")) {
        nextTermIsNegative = true;
      } else if (termToken.equals("+")) {
        nextTermIsNegative = false;
      } else {
        // Not "+" or "-"; must be a term
        RatTerm term = RatTerm.valueOf(termToken);

        // at this point, coeff and expt are initialized.
        // Need to fix coeff if it was preceeded by a '-'
        if (nextTermIsNegative) {
          term = term.negate();
        }

        // accumulate terms of polynomial in 'parsedTerms'
        sortedInsert(parsedTerms, term);
      }
    }
    return new RatPoly(parsedTerms);
  }

  /**
   * Standard hashCode function.
   *
   * @return an int that all objects equal to this will also return.
   */
  @Override
  public int hashCode() {
    // all instances that are NaN must return the same hashcode;
    if (this.isNaN()) {
      return 0;
    }
    return terms.hashCode();
  }

  /**
   * Standard equality operation.
   *
   * @param obj The object to be compared for equality.
   * @return true if and only if 'obj' is an instance of a RatPoly and 'this'
   *         and 'obj' represent the same rational polynomial. Note that all
   *         NaN RatPolys are equal.
   */
  @Override
  public boolean equals(/*@Nullable*/ Object obj) {
    if (obj instanceof RatPoly) {
      RatPoly rp = (RatPoly) obj;

      // special case: check if both are NaN
      if (this.isNaN() && rp.isNaN()) {
        return true;
      } else {
        return terms.equals(rp.terms);
      }
    } else {
      return false;
    }
  }

  /**
   * Checks that the representation invariant holds (if any).
   */
  private void checkRep() {
    assert (terms != null);
    
    for (int i = 0; i < terms.size(); i++) {
        assert (!terms.get(i).getCoeff().equals(new RatNum(0))) : "zero coefficient";
        assert (terms.get(i).getExpt() >= 0) : "negative exponent";
        
        if (i < terms.size() - 1)
            assert (terms.get(i + 1).getExpt() < terms.get(i).getExpt()) : "terms out of order";
    }
  }
}
