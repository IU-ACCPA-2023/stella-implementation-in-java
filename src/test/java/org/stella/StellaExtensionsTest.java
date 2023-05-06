package org.stella;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class StellaExtensionsTest {

    protected static final String TEST_FOLDER = "tests/";
    protected static final String WELL = "well-typed";
    protected static final String ILL = "ill-typed";

    @TestFactory
    @DisplayName("Testing Stella Extensions")
    Stream<DynamicContainer> dynamicTestsFromStream() {
        return getExtensions()
                .map(extension -> DynamicContainer.dynamicContainer(extension,
                        Stream.of(
                                DynamicContainer.dynamicContainer(WELL, getWellTests(extension)),
                                DynamicContainer.dynamicContainer(ILL, getIllTests(extension))
                        )));
    }


    //////////////////////////////////////////////////////////////////
    // Methods for retrieving extensions

    /* All extension folders should be inside tests/ directory and must contain
        both ill- and well-typed folders with tests. */
    private Stream<String> getExtensions() {
        return Stream.of(new File(TEST_FOLDER).listFiles())
                .filter(File::isDirectory)
                .filter(this::containsWellAndIllTyped)
                .map(File::getName)
                .sorted();
    }

    private boolean containsWellAndIllTyped(File file) {
        var content = file.list();
        boolean wellPresent = false;
        boolean illPresent = false;
        for (var name : content) {
            wellPresent |= name.equals(WELL);
            illPresent |= name.equals(ILL);
        }
        return wellPresent && illPresent;
    }


    //////////////////////////////////////////////////////////////////
    // Methods for retrieving tests

    private Stream<DynamicTest> getWellTests(String extensionName){
        var tests = getTests(extensionName, WELL);
        return IntStream
                .range(0, tests.size())
                .mapToObj(i -> DynamicTest.dynamicTest(
                        (1 + i) + " Typechecking " + WELL + " program " + tests.get(i),
                        () -> testWell(TEST_FOLDER + extensionName + "/" + WELL + "/" + tests.get(i))));
    }

    private Stream<DynamicTest> getIllTests(String extensionName){
        var tests = getTests(extensionName, ILL);
        return IntStream
                .range(0, tests.size())
                .mapToObj(i -> DynamicTest.dynamicTest(
                        (1 + i) + " Typechecking " + ILL + " program " + tests.get(i),
                        () -> testIll(TEST_FOLDER + extensionName + "/" + ILL + "/" + tests.get(i))));
    }

    private List<String> getTests(String extensionName, String type){
        var path = TEST_FOLDER + extensionName + "/" + type;
        return Stream.of(Objects.requireNonNull(new File(path).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toList());
    }


    //////////////////////////////////////////////////////////////////
    // Methods for type-checking well- and ill-typed test cases

    public void testWell(String path) throws Exception {
        String[] args = new String[0];
        final InputStream original = System.in;
        final FileInputStream fips = new FileInputStream(path);
        System.setIn(fips);
        Assertions.assertDoesNotThrow(() -> Main.main(args));
        System.setIn(original);
    }

    public void testIll(String path) throws Exception {
        String[] args = new String[0];
        final FileInputStream fips = new FileInputStream(path);
        System.setIn(fips);

        // Change Exception class to your specific
        Exception exception = assertThrows(Exception.class, () -> Main.main(args), "Expected the type checker to fail!");
        System.out.println("Type Error: " + exception.getMessage());
    }
}
