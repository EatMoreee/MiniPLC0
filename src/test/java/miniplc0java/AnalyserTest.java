package miniplc0java;

import miniplc0java.analyser.Analyser;
import miniplc0java.error.CompileError;
import miniplc0java.error.TokenizeError;
import miniplc0java.tokenizer.StringIter;
import miniplc0java.tokenizer.Token;
import miniplc0java.tokenizer.Tokenizer;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.*;

public class AnalyserTest {
    @Test
    public void testTokenizer() throws FileNotFoundException, CompileError {
        File file = new File("D:\\buaa\\2020autumn\\编译原理与技术\\语法分析\\java\\txt\\code.txt");
        Scanner sc = new Scanner(file);
        StringIter it = new StringIter(sc);
        Tokenizer tokenizer = new Tokenizer(it);
        Analyser analyser = new Analyser(tokenizer);
        analyser.analyse();
    }

}
