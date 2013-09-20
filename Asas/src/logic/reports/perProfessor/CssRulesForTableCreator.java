package logic.reports.perProfessor;

import logic.html.CssRule;
import logic.html.StyleTag;

public class CssRulesForTableCreator {

	public StyleTag getStyleTag(){
		return new StyleTag().
				addCssRule(createRuleForEvenRows()).
				addCssRule(createRuleForOddRows()).
				addCssRule(createRuleForProfessorDetailsCell()).
				addCssRule(createRuleForProfessorCell());
	}
	
	public static String getProfessorCellClassName(){
		return "professorCell";
	}
	
	public static String getProfessorDetailsCellClassName(){
		return "professorDetails";
	}
	
	public static String getEvenRowClassName(){
		return "even";
	}
	
	public static String getOddRowClassName(){
		return "odd";
	}
	
	private CssRule createRuleForProfessorCell(){
		return new CssRule().addClassSelector(getProfessorCellClassName()).addStyle("paddin-top", "20px");
	}
	
	private CssRule createRuleForProfessorDetailsCell(){
		CssRule rule = new CssRule();
		rule.addClassSelector(getProfessorDetailsCellClassName());
		rule.addStyle("padding-left", "20px");
		rule.addStyle("border-radius", "5");
		rule.addStyle("text-align", "left");
		return rule;
	}
	
	private CssRule createRuleForEvenRows(){
		CssRule rule = new CssRule();
		rule.addClassSelector(getEvenRowClassName());
		rule.addStyle("background-color", "57aedf");
		return rule;
	}

	private CssRule createRuleForOddRows(){
		CssRule rule = new CssRule();
		rule.addClassSelector(getOddRowClassName());
		rule.addStyle("background-color", "b1cff5");
		return rule;
	}
	
}
