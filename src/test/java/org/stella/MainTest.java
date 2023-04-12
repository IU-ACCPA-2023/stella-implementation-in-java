package org.stella;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.stella.typecheck.VisitTypeCheck;

import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

class MainTest {


    @ParameterizedTest(name = "{index} Typechecking well-typed program {0}")
    @ValueSource(strings = {
            "tests/well-typed/abstract-function.stella",
            "tests/well-typed/added-test-1.stella",
            "tests/well-typed/added-test-2.stella",
            "tests/well-typed/apply-increase.stella",
            "tests/well-typed/applying-actual-function-3.stella",
            "tests/well-typed/bool-to-nat.stella",
            "tests/well-typed/cubes.stella",
            "tests/well-typed/double-application.stella",
            "tests/well-typed/factorial.stella",
            "tests/well-typed/good-if.stella",
            "tests/well-typed/good-if-2.stella",
            "tests/well-typed/good-succ-1.stella",
            "tests/well-typed/good-succ-2.stella",
            "tests/well-typed/higher-order-1.stella",
            "tests/well-typed/higher-order-2.stella",
            "tests/well-typed/increment-triple.stella",
            "tests/well-typed/increment_twice.stella",
            "tests/well-typed/inner-if.stella",
            "tests/well-typed/logical-operators.stella",
            "tests/well-typed/many-if.stella",
            "tests/well-typed/my-good-if.stella",
            "tests/well-typed/my-good-succ.stella",
            "tests/well-typed/my-well-typed-1.stella",
            "tests/well-typed/my-well-typed-2.stella",
            "tests/well-typed/nat-to-bool.stella",
            "tests/well-typed/negate.stella",
            "tests/well-typed/nested.stella",
            "tests/well-typed/shadowed-variable-2.stella",
            "tests/well-typed/simple-succ.stella",
            "tests/well-typed/simple-types.stella",
            "tests/well-typed/squares.stella",
            "tests/well-typed/succ-with-func.stella",
            "tests/well-typed/x-simple-if.stella",
    })
    public void testWellTyped(String filepath) throws IOException, Exception {
        String[] args = new String[0];
        final InputStream original = System.in;
        final FileInputStream fips = new FileInputStream(new File(filepath));
        System.setIn(fips);
        Main.main(args);
        System.setIn(original);
    }

    @ParameterizedTest(name = "{index} Typechecking ill-typed program {0}")
    @ValueSource(strings = {
            "tests/ill-typed/added-test-1.stella",
            "tests/ill-typed/added-test-2.stella",
            "tests/ill-typed/application-param-type.stella",
            "tests/ill-typed/applying-non-function-1.stella",
            "tests/ill-typed/applying-non-function-2.stella",
            "tests/ill-typed/applying-non-function-3.stella",
            "tests/ill-typed/argument-type-mismatch-1.stella",
            "tests/ill-typed/argument-type-mismatch-2.stella",
            "tests/ill-typed/argument-type-mismatch-3.stella",
            "tests/ill-typed/bad-abstraction.stella",
            "tests/ill-typed/bad-factorial.stella",
            "tests/ill-typed/bad-factorial-1.stella",
            "tests/ill-typed/bad-factorial-2.stella",
            "tests/ill-typed/bad-function-call.stella",
            "tests/ill-typed/bad-higher-order-1.stella",
            "tests/ill-typed/bad-if-1.stella",
            "tests/ill-typed/bad-if-2.stella",
            "tests/ill-typed/bad-if-3.stella",
            "tests/ill-typed/bad-if-4.stella",
            "tests/ill-typed/bad-if-and-undefined-variable-1.stella",
            "tests/ill-typed/bad-iszero.stella",
            "tests/ill-typed/bad-logic-not-1.stella",
            "tests/ill-typed/bad-nat-1.stella",
            "tests/ill-typed/bad-nat-2.stella",
            "tests/ill-typed/bad-nat-rec-1.stella",
            "tests/ill-typed/bad-nat-rec-2.stella",
            "tests/ill-typed/bad-return-type.stella",
            "tests/ill-typed/bad-squares-1.stella",
            "tests/ill-typed/bad-squares-2.stella",
            "tests/ill-typed/bad-succ-1.stella",
            "tests/ill-typed/bad-succ-2.stella",
            "tests/ill-typed/bad-succ-3.stella",
            "tests/ill-typed/function-mismatch.stella",
            "tests/ill-typed/invalid-nat.stella",
            "tests/ill-typed/invalid-not_.stella",
            "tests/ill-typed/my-factorial.stella",
            "tests/ill-typed/my-ill-test-2.stella",
            "tests/ill-typed/my-ill-typed-1.stella",
            "tests/ill-typed/my-ill-typed-2.stella",
            "tests/ill-typed/my-mismatch.stella",
            "tests/ill-typed/nat__rec-parameters.stella",
            "tests/ill-typed/shadowed-variable-1.stella",
            "tests/ill-typed/shadowed-variable-2.stella",
            "tests/ill-typed/undefined-variable-1.stella",
            "tests/ill-typed/undefined-variable-2.stella",
            "tests/ill-typed/undefined-variable-3.stella",
            })
    public void testIllTyped(String filepath) throws IOException, Exception {
        String[] args = new String[0];
        final InputStream original = System.in;
        final FileInputStream fips = new FileInputStream(new File(filepath));
        System.setIn(fips);

        boolean typecheckerFailed = false;
        try {
            Main.main(args); // TODO: check that if it fail then there is a type error actually, and not a problem with implementation
        } catch (VisitTypeCheck.TypeError e) {
            System.out.println("Type Error: " + e.getMessage());
            typecheckerFailed = true;
        }
        if (!typecheckerFailed) {
            throw new Exception("expected the typechecker to fail!");
        }
        // System.setIn(original); // dead code
    }
}
