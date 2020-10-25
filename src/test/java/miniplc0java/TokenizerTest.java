package miniplc0java;

import org.junit.Test;
import static org.junit.Assert.*;
import miniplc0java.error.ErrorCode;
import miniplc0java.error.TokenizeError;
import miniplc0java.tokenizer.StringIter;
import miniplc0java.tokenizer.Token;
import miniplc0java.tokenizer.TokenType;
import miniplc0java.tokenizer.Tokenizer;
import miniplc0java.util.Pos;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class TokenizerTest {
    @Test
    public void testTokenizer() throws FileNotFoundException, TokenizeError {
        File file = new File("D:\\buaa\\2020autumn\\编译原理与技术\\语法分析\\java\\txt\\code.txt");
        Scanner sc = new Scanner(file);
        StringIter it = new StringIter(sc);
        Tokenizer tokenizer = new Tokenizer(it);
        while(true) {
            Token token = tokenizer.nextToken();
            if(token.getValueString().equals(""))
                break;
            System.out.println(token.getTokenType().toString() + ", " + token.getValueString() + " " + token.getStartPos() + " " + token.getEndPos());
        }
    }
}