// File generated by the BNF Converter (bnfc 2.9.4.1).

package org.stella.typecheck;

import org.syntax.stella.Absyn.*;
import org.syntax.stella.PrettyPrinter;

import javax.sound.sampled.TargetDataLine;
import java.lang.reflect.AccessibleObject;
import java.util.HashMap;
import java.util.LinkedList;

/*** Visitor Design Pattern for TypeCheck. ***/

/* This implements the common visitor design pattern.
   Tests show it to be slightly less efficient than the
   instanceof method, but easier to use.
   Replace the org.syntax.stella.Absyn.Type and ContextAndReturnType parameters with the desired return
   and context types.*/

public class VisitTypeCheck
{
  public final class ContextAndReturnType {
    HashMap<String, org.syntax.stella.Absyn.Type> context;
    org.syntax.stella.Absyn.Type returnType;

    public ContextAndReturnType(HashMap<String, Type> context, Type returnType) {
      this.context = context;
      this.returnType = returnType;
    }
  }

  public class TypeError extends RuntimeException {
    public TypeError(String message) {
      super(message);
    }
  }

  public Type compareTypes(Expr e, Type actualType, Type expectedType) {
    if (expectedType == null) {
      return actualType;
    }
    if (actualType.equals(expectedType)) {
      return expectedType;
    }
    throw new TypeError("expected " + PrettyPrinter.print(expectedType) + " but got " + PrettyPrinter.print(actualType) + " for expression " + PrettyPrinter.print(e));
  }

  public class ProgramVisitor implements org.syntax.stella.Absyn.Program.Visitor<org.syntax.stella.Absyn.Type, ContextAndReturnType>
  {
    public org.syntax.stella.Absyn.Type visit(org.syntax.stella.Absyn.AProgram p, ContextAndReturnType arg)
    { /* Code for AProgram goes here */
      p.languagedecl_.accept(new LanguageDeclVisitor(), arg);
      for (org.syntax.stella.Absyn.Extension x: p.listextension_) {
        x.accept(new ExtensionVisitor(), arg);
      }
      for (org.syntax.stella.Absyn.Decl x: p.listdecl_) {
        x.accept(new DeclVisitor(), arg);
      }
      return null;
    }
  }
  public class LanguageDeclVisitor implements org.syntax.stella.Absyn.LanguageDecl.Visitor<org.syntax.stella.Absyn.Type, ContextAndReturnType>
  {
    public org.syntax.stella.Absyn.Type visit(org.syntax.stella.Absyn.LanguageCore p, ContextAndReturnType arg)
    { /* Code for LanguageCore goes here */
      return null;
    }
  }
  public class ExtensionVisitor implements org.syntax.stella.Absyn.Extension.Visitor<org.syntax.stella.Absyn.Type, ContextAndReturnType>
  {
    public org.syntax.stella.Absyn.Type visit(org.syntax.stella.Absyn.AnExtension p, ContextAndReturnType arg)
    { /* Code for AnExtension goes here */
      for (String x: p.listextensionname_) {
        //x;
      }
      return null;
    }
  }
  public class DeclVisitor implements org.syntax.stella.Absyn.Decl.Visitor<org.syntax.stella.Absyn.Type, ContextAndReturnType>
  {
    public org.syntax.stella.Absyn.Type visit(org.syntax.stella.Absyn.DeclFun p, ContextAndReturnType arg)
    { /* Code for DeclFun goes here */
      System.out.println("Visiting declaration of function " + p.stellaident_);

      for (org.syntax.stella.Absyn.Annotation x: p.listannotation_) {
        x.accept(new AnnotationVisitor(), arg);
      }
      //p.stellaident_;
      for (org.syntax.stella.Absyn.ParamDecl x: p.listparamdecl_) {
        x.accept(new ParamDeclVisitor(), arg);
      }
      p.returntype_.accept(new ReturnTypeVisitor(), arg);
      p.throwtype_.accept(new ThrowTypeVisitor(), arg);
      for (org.syntax.stella.Absyn.Decl x: p.listdecl_) {
        x.accept(new DeclVisitor(), arg);
      }

      HashMap newContext = new HashMap<>(arg.context);
      AParamDecl paramDecl = (AParamDecl)p.listparamdecl_.get(0);
      newContext.put(paramDecl.stellaident_, paramDecl.type_);

      Type returnType = p.returntype_.accept(new ReturnType.Visitor<Type, Object>() {
        @Override
        public Type visit(NoReturnType p, Object arg) {
          return null;
        }

        @Override
        public Type visit(SomeReturnType p, Object arg) {
          return p.type_;
        }
      }, null);

      p.expr_.accept(new ExprVisitor(), new ContextAndReturnType(newContext, returnType));

      ListType argListType = new ListType();
      argListType.add(paramDecl.type_);
      arg.context.put(p.stellaident_, new TypeFun(argListType, returnType));
      return null;
    }
    public org.syntax.stella.Absyn.Type visit(org.syntax.stella.Absyn.DeclTypeAlias p, ContextAndReturnType arg)
    { /* Code for DeclTypeAlias goes here */
      //p.stellaident_;
      p.type_.accept(new TypeVisitor(), arg);
      return null;
    }
  }
  public class LocalDeclVisitor implements org.syntax.stella.Absyn.LocalDecl.Visitor<org.syntax.stella.Absyn.Type, ContextAndReturnType>
  {
    public Type visit(org.syntax.stella.Absyn.ALocalDecl p, ContextAndReturnType arg)
    { /* Code for ALocalDecl goes here */
      p.decl_.accept(new DeclVisitor(), arg);
      return null;
    }
  }
  public class AnnotationVisitor implements org.syntax.stella.Absyn.Annotation.Visitor<org.syntax.stella.Absyn.Type, ContextAndReturnType>
  {
    public Type visit(org.syntax.stella.Absyn.InlineAnnotation p, ContextAndReturnType arg)
    { /* Code for InlineAnnotation goes here */
      return null;
    }
  }
  public class ParamDeclVisitor implements org.syntax.stella.Absyn.ParamDecl.Visitor<org.syntax.stella.Absyn.Type, ContextAndReturnType>
  {
    public Type visit(org.syntax.stella.Absyn.AParamDecl p, ContextAndReturnType arg)
    { /* Code for AParamDecl goes here */
      //p.stellaident_;
      p.type_.accept(new TypeVisitor(), arg);
      return null;
    }
  }
  public class ReturnTypeVisitor implements org.syntax.stella.Absyn.ReturnType.Visitor<org.syntax.stella.Absyn.Type, ContextAndReturnType>
  {
    public Type visit(org.syntax.stella.Absyn.NoReturnType p, ContextAndReturnType arg)
    { /* Code for NoReturnType goes here */
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.SomeReturnType p, ContextAndReturnType arg)
    { /* Code for SomeReturnType goes here */
      p.type_.accept(new TypeVisitor(), arg);
      return null;
    }
  }
  public class ThrowTypeVisitor implements org.syntax.stella.Absyn.ThrowType.Visitor<org.syntax.stella.Absyn.Type, ContextAndReturnType>
  {
    public Type visit(org.syntax.stella.Absyn.NoThrowType p, ContextAndReturnType arg)
    { /* Code for NoThrowType goes here */
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.SomeThrowType p, ContextAndReturnType arg)
    { /* Code for SomeThrowType goes here */
      for (org.syntax.stella.Absyn.Type x: p.listtype_) {
        x.accept(new TypeVisitor(), arg);
      }
      return null;
    }
  }
  public class TypeVisitor implements org.syntax.stella.Absyn.Type.Visitor<org.syntax.stella.Absyn.Type, ContextAndReturnType>
  {
    public Type visit(org.syntax.stella.Absyn.TypeFun p, ContextAndReturnType arg)
    { /* Code for TypeFun goes here */
      for (org.syntax.stella.Absyn.Type x: p.listtype_) {
        x.accept(new TypeVisitor(), arg);
      }
      p.type_.accept(new TypeVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.TypeRec p, ContextAndReturnType arg)
    { /* Code for TypeRec goes here */
      //p.stellaident_;
      p.type_.accept(new TypeVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.TypeSum p, ContextAndReturnType arg)
    { /* Code for TypeSum goes here */
      p.type_1.accept(new TypeVisitor(), arg);
      p.type_2.accept(new TypeVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.TypeTuple p, ContextAndReturnType arg)
    { /* Code for TypeTuple goes here */
      for (org.syntax.stella.Absyn.Type x: p.listtype_) {
        x.accept(new TypeVisitor(), arg);
      }
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.TypeRecord p, ContextAndReturnType arg)
    { /* Code for TypeRecord goes here */
      for (org.syntax.stella.Absyn.RecordFieldType x: p.listrecordfieldtype_) {
        x.accept(new RecordFieldTypeVisitor(), arg);
      }
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.TypeVariant p, ContextAndReturnType arg)
    { /* Code for TypeVariant goes here */
      for (org.syntax.stella.Absyn.VariantFieldType x: p.listvariantfieldtype_) {
        x.accept(new VariantFieldTypeVisitor(), arg);
      }
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.TypeList p, ContextAndReturnType arg)
    { /* Code for TypeList goes here */
      p.type_.accept(new TypeVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.TypeBool p, ContextAndReturnType arg)
    { /* Code for TypeBool goes here */
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.TypeNat p, ContextAndReturnType arg)
    { /* Code for TypeNat goes here */
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.TypeUnit p, ContextAndReturnType arg)
    { /* Code for TypeUnit goes here */
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.TypeVar p, ContextAndReturnType arg)
    { /* Code for TypeVar goes here */
      //p.stellaident_;
      return null;
    }
  }
  public class MatchCaseVisitor implements org.syntax.stella.Absyn.MatchCase.Visitor<org.syntax.stella.Absyn.Type, ContextAndReturnType>
  {
    public Type visit(org.syntax.stella.Absyn.AMatchCase p, ContextAndReturnType arg)
    { /* Code for AMatchCase goes here */
      p.pattern_.accept(new PatternVisitor(), arg);
      p.expr_.accept(new ExprVisitor(), arg);
      return null;
    }
  }
  public class OptionalTypingVisitor implements org.syntax.stella.Absyn.OptionalTyping.Visitor<org.syntax.stella.Absyn.Type, ContextAndReturnType>
  {
    public Type visit(org.syntax.stella.Absyn.NoTyping p, ContextAndReturnType arg)
    { /* Code for NoTyping goes here */
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.SomeTyping p, ContextAndReturnType arg)
    { /* Code for SomeTyping goes here */
      p.type_.accept(new TypeVisitor(), arg);
      return null;
    }
  }
  public class PatternDataVisitor implements org.syntax.stella.Absyn.PatternData.Visitor<org.syntax.stella.Absyn.Type, ContextAndReturnType>
  {
    public Type visit(org.syntax.stella.Absyn.NoPatternData p, ContextAndReturnType arg)
    { /* Code for NoPatternData goes here */
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.SomePatternData p, ContextAndReturnType arg)
    { /* Code for SomePatternData goes here */
      p.pattern_.accept(new PatternVisitor(), arg);
      return null;
    }
  }
  public class ExprDataVisitor implements org.syntax.stella.Absyn.ExprData.Visitor<org.syntax.stella.Absyn.Type, ContextAndReturnType>
  {
    public Type visit(org.syntax.stella.Absyn.NoExprData p, ContextAndReturnType arg)
    { /* Code for NoExprData goes here */
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.SomeExprData p, ContextAndReturnType arg)
    { /* Code for SomeExprData goes here */
      p.expr_.accept(new ExprVisitor(), arg);
      return null;
    }
  }
  public class PatternVisitor implements org.syntax.stella.Absyn.Pattern.Visitor<org.syntax.stella.Absyn.Type, ContextAndReturnType>
  {
    public Type visit(org.syntax.stella.Absyn.PatternVariant p, ContextAndReturnType arg)
    { /* Code for PatternVariant goes here */
      //p.stellaident_;
      p.patterndata_.accept(new PatternDataVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.PatternInl p, ContextAndReturnType arg)
    { /* Code for PatternInl goes here */
      p.pattern_.accept(new PatternVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.PatternInr p, ContextAndReturnType arg)
    { /* Code for PatternInr goes here */
      p.pattern_.accept(new PatternVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.PatternTuple p, ContextAndReturnType arg)
    { /* Code for PatternTuple goes here */
      for (org.syntax.stella.Absyn.Pattern x: p.listpattern_) {
        x.accept(new PatternVisitor(), arg);
      }
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.PatternRecord p, ContextAndReturnType arg)
    { /* Code for PatternRecord goes here */
      for (org.syntax.stella.Absyn.LabelledPattern x: p.listlabelledpattern_) {
        x.accept(new LabelledPatternVisitor(), arg);
      }
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.PatternList p, ContextAndReturnType arg)
    { /* Code for PatternList goes here */
      for (org.syntax.stella.Absyn.Pattern x: p.listpattern_) {
        x.accept(new PatternVisitor(), arg);
      }
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.PatternCons p, ContextAndReturnType arg)
    { /* Code for PatternCons goes here */
      p.pattern_1.accept(new PatternVisitor(), arg);
      p.pattern_2.accept(new PatternVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.PatternFalse p, ContextAndReturnType arg)
    { /* Code for PatternFalse goes here */
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.PatternTrue p, ContextAndReturnType arg)
    { /* Code for PatternTrue goes here */
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.PatternUnit p, ContextAndReturnType arg)
    { /* Code for PatternUnit goes here */
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.PatternInt p, ContextAndReturnType arg)
    { /* Code for PatternInt goes here */
      //p.integer_;
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.PatternSucc p, ContextAndReturnType arg)
    { /* Code for PatternSucc goes here */
      p.pattern_.accept(new PatternVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.PatternVar p, ContextAndReturnType arg)
    { /* Code for PatternVar goes here */
      //p.stellaident_;
      return null;
    }
  }
  public class LabelledPatternVisitor implements org.syntax.stella.Absyn.LabelledPattern.Visitor<org.syntax.stella.Absyn.Type, ContextAndReturnType>
  {
    public Type visit(org.syntax.stella.Absyn.ALabelledPattern p, ContextAndReturnType arg)
    { /* Code for ALabelledPattern goes here */
      //p.stellaident_;
      p.pattern_.accept(new PatternVisitor(), arg);
      return null;
    }
  }
  public class BindingVisitor implements org.syntax.stella.Absyn.Binding.Visitor<org.syntax.stella.Absyn.Type, ContextAndReturnType>
  {
    public Type visit(org.syntax.stella.Absyn.ABinding p, ContextAndReturnType arg)
    { /* Code for ABinding goes here */
      //p.stellaident_;
      p.expr_.accept(new ExprVisitor(), arg);
      return null;
    }
  }
  public class ExprVisitor implements org.syntax.stella.Absyn.Expr.Visitor<org.syntax.stella.Absyn.Type, ContextAndReturnType>
  {
    public Type visit(org.syntax.stella.Absyn.Sequence p, ContextAndReturnType arg)
    { /* Code for Sequence goes here */
      p.expr_1.accept(new ExprVisitor(), arg);
      p.expr_2.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.If p, ContextAndReturnType arg)
    { /* Code for If goes here */
      p.expr_1.accept(new ExprVisitor(), arg);
      p.expr_2.accept(new ExprVisitor(), arg);
      p.expr_3.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.Let p, ContextAndReturnType arg)
    { /* Code for Let goes here */
      for (org.syntax.stella.Absyn.PatternBinding x: p.listpatternbinding_) {
        x.accept(new PatternBindingVisitor(), arg);
      }
      p.expr_.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.LetRec p, ContextAndReturnType arg)
    { /* Code for LetRec goes here */
      for (org.syntax.stella.Absyn.PatternBinding x: p.listpatternbinding_) {
        x.accept(new PatternBindingVisitor(), arg);
      }
      p.expr_.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.LessThan p, ContextAndReturnType arg)
    { /* Code for LessThan goes here */
      p.expr_1.accept(new ExprVisitor(), arg);
      p.expr_2.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.LessThanOrEqual p, ContextAndReturnType arg)
    { /* Code for LessThanOrEqual goes here */
      p.expr_1.accept(new ExprVisitor(), arg);
      p.expr_2.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.GreaterThan p, ContextAndReturnType arg)
    { /* Code for GreaterThan goes here */
      p.expr_1.accept(new ExprVisitor(), arg);
      p.expr_2.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.GreaterThanOrEqual p, ContextAndReturnType arg)
    { /* Code for GreaterThanOrEqual goes here */
      p.expr_1.accept(new ExprVisitor(), arg);
      p.expr_2.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.Equal p, ContextAndReturnType arg)
    { /* Code for Equal goes here */
      p.expr_1.accept(new ExprVisitor(), arg);
      p.expr_2.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.NotEqual p, ContextAndReturnType arg)
    { /* Code for NotEqual goes here */
      p.expr_1.accept(new ExprVisitor(), arg);
      p.expr_2.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.TypeAsc p, ContextAndReturnType arg)
    { /* Code for TypeAsc goes here */
      p.expr_.accept(new ExprVisitor(), arg);
      p.type_.accept(new TypeVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.Abstraction p, ContextAndReturnType arg)
    { /* Code for Abstraction goes here */
      for (org.syntax.stella.Absyn.ParamDecl x: p.listparamdecl_) {
        x.accept(new ParamDeclVisitor(), arg);
      }
      p.expr_.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.Variant p, ContextAndReturnType arg)
    { /* Code for Variant goes here */
      //p.stellaident_;
      p.exprdata_.accept(new ExprDataVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.Match p, ContextAndReturnType arg)
    { /* Code for Match goes here */
      p.expr_.accept(new ExprVisitor(), arg);
      for (org.syntax.stella.Absyn.MatchCase x: p.listmatchcase_) {
        x.accept(new MatchCaseVisitor(), arg);
      }
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.List p, ContextAndReturnType arg)
    { /* Code for List goes here */
      for (org.syntax.stella.Absyn.Expr x: p.listexpr_) {
        x.accept(new ExprVisitor(), arg);
      }
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.Add p, ContextAndReturnType arg)
    { /* Code for Add goes here */
      p.expr_1.accept(new ExprVisitor(), arg);
      p.expr_2.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.Subtract p, ContextAndReturnType arg)
    { /* Code for Subtract goes here */
      p.expr_1.accept(new ExprVisitor(), arg);
      p.expr_2.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.LogicOr p, ContextAndReturnType arg)
    { /* Code for LogicOr goes here */
      p.expr_1.accept(new ExprVisitor(), arg);
      p.expr_2.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.Multiply p, ContextAndReturnType arg)
    { /* Code for Multiply goes here */
      p.expr_1.accept(new ExprVisitor(), arg);
      p.expr_2.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.Divide p, ContextAndReturnType arg)
    { /* Code for Divide goes here */
      p.expr_1.accept(new ExprVisitor(), arg);
      p.expr_2.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.LogicAnd p, ContextAndReturnType arg)
    { /* Code for LogicAnd goes here */
      p.expr_1.accept(new ExprVisitor(), arg);
      p.expr_2.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.Application p, ContextAndReturnType arg)
    { /* Code for Application goes here */
      p.expr_.accept(new ExprVisitor(), arg);
      for (org.syntax.stella.Absyn.Expr x: p.listexpr_) {
        x.accept(new ExprVisitor(), arg);
      }
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.DotRecord p, ContextAndReturnType arg)
    { /* Code for DotRecord goes here */
      p.expr_.accept(new ExprVisitor(), arg);
      //p.stellaident_;
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.DotTuple p, ContextAndReturnType arg)
    { /* Code for DotTuple goes here */
      p.expr_.accept(new ExprVisitor(), arg);
      //p.integer_;
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.Tuple p, ContextAndReturnType arg)
    { /* Code for Tuple goes here */
      for (org.syntax.stella.Absyn.Expr x: p.listexpr_) {
        x.accept(new ExprVisitor(), arg);
      }
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.Record p, ContextAndReturnType arg)
    { /* Code for Record goes here */
      for (org.syntax.stella.Absyn.Binding x: p.listbinding_) {
        x.accept(new BindingVisitor(), arg);
      }
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.ConsList p, ContextAndReturnType arg)
    { /* Code for ConsList goes here */
      p.expr_1.accept(new ExprVisitor(), arg);
      p.expr_2.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.Head p, ContextAndReturnType arg)
    { /* Code for Head goes here */
      p.expr_.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.IsEmpty p, ContextAndReturnType arg)
    { /* Code for IsEmpty goes here */
      p.expr_.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.Tail p, ContextAndReturnType arg)
    { /* Code for Tail goes here */
      p.expr_.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.Inl p, ContextAndReturnType arg)
    { /* Code for Inl goes here */
      p.expr_.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.Inr p, ContextAndReturnType arg)
    { /* Code for Inr goes here */
      p.expr_.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.Succ p, ContextAndReturnType arg)
    { /* Code for Succ goes here */
      p.expr_.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.LogicNot p, ContextAndReturnType arg)
    { /* Code for LogicNot goes here */
      p.expr_.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.Pred p, ContextAndReturnType arg)
    { /* Code for Pred goes here */
      p.expr_.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.IsZero p, ContextAndReturnType arg)
    { /* Code for IsZero goes here */
      p.expr_.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.Fix p, ContextAndReturnType arg)
    { /* Code for Fix goes here */
      p.expr_.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.NatRec p, ContextAndReturnType arg)
    { /* Code for NatRec goes here */
      p.expr_1.accept(new ExprVisitor(), arg);
      p.expr_2.accept(new ExprVisitor(), arg);
      p.expr_3.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.Fold p, ContextAndReturnType arg)
    { /* Code for Fold goes here */
      p.type_.accept(new TypeVisitor(), arg);
      p.expr_.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.Unfold p, ContextAndReturnType arg)
    { /* Code for Unfold goes here */
      p.type_.accept(new TypeVisitor(), arg);
      p.expr_.accept(new ExprVisitor(), arg);
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.ConstTrue p, ContextAndReturnType arg)
    { /* Code for ConstTrue goes here */
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.ConstFalse p, ContextAndReturnType arg)
    { /* Code for ConstFalse goes here */
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.ConstUnit p, ContextAndReturnType arg)
    { /* Code for ConstUnit goes here */
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.ConstInt p, ContextAndReturnType arg)
    { /* Code for ConstInt goes here */
      //p.integer_;
      return null;
    }
    public Type visit(org.syntax.stella.Absyn.Var p, ContextAndReturnType arg)
    { /* Code for Var goes here */
      //p.stellaident_;
      return null;
    }
  }
  public class PatternBindingVisitor implements org.syntax.stella.Absyn.PatternBinding.Visitor<org.syntax.stella.Absyn.Type, ContextAndReturnType>
  {
    public Type visit(org.syntax.stella.Absyn.APatternBinding p, ContextAndReturnType arg)
    { /* Code for APatternBinding goes here */
      p.pattern_.accept(new PatternVisitor(), arg);
      p.expr_.accept(new ExprVisitor(), arg);
      return null;
    }
  }
  public class VariantFieldTypeVisitor implements org.syntax.stella.Absyn.VariantFieldType.Visitor<org.syntax.stella.Absyn.Type, ContextAndReturnType>
  {
    public Type visit(org.syntax.stella.Absyn.AVariantFieldType p, ContextAndReturnType arg)
    { /* Code for AVariantFieldType goes here */
      //p.stellaident_;
      p.optionaltyping_.accept(new OptionalTypingVisitor(), arg);
      return null;
    }
  }
  public class RecordFieldTypeVisitor implements org.syntax.stella.Absyn.RecordFieldType.Visitor<org.syntax.stella.Absyn.Type, ContextAndReturnType>
  {
    public Type visit(org.syntax.stella.Absyn.ARecordFieldType p, ContextAndReturnType arg)
    { /* Code for ARecordFieldType goes here */
      //p.stellaident_;
      p.type_.accept(new TypeVisitor(), arg);
      return null;
    }
  }
  public class TypingVisitor implements org.syntax.stella.Absyn.Typing.Visitor<org.syntax.stella.Absyn.Type, ContextAndReturnType>
  {
    public Type visit(org.syntax.stella.Absyn.ATyping p, ContextAndReturnType arg)
    { /* Code for ATyping goes here */
      p.expr_.accept(new ExprVisitor(), arg);
      p.type_.accept(new TypeVisitor(), arg);
      return null;
    }
  }
}
