package miniplc0java.tokenizer;

import miniplc0java.error.TokenizeError;
import miniplc0java.error.ErrorCode;
import miniplc0java.util.Pos;

public class Tokenizer {

    private StringIter it;

    public Tokenizer(StringIter it) {
        this.it = it;
    }

    // 这里本来是想实现 Iterator<Token> 的，但是 Iterator 不允许抛异常，于是就这样了
    /**
     * 获取下一个 Token
     *
     * @return
     * @throws TokenizeError 如果解析有异常则抛出
     */
    public Token nextToken() throws TokenizeError {
        it.readAll();

        // 跳过之前的所有空白字符
        skipSpaceCharacters();

        if (it.isEOF()) {
            return new Token(TokenType.EOF, "", it.currentPos(), it.currentPos());
        }

        char peek = it.peekChar();
        if (Character.isDigit(peek)) {
            return lexUInt();
        } else if (Character.isAlphabetic(peek)) {
            return lexIdentOrKeyword();
        } else {
            return lexOperatorOrUnknown();
        }
    }

    private Token lexUInt() throws TokenizeError {
        // 请填空：
        StringBuffer tokenStr = new StringBuffer();//单词的字符串
        Pos startPos = it.currentPos();//记录Pos初始位置
        // 直到查看下一个字符不是数字为止:
        while(Character.isDigit(it.peekChar())){
            // -- 前进一个字符，并存储这个字符
            tokenStr.append(it.nextChar());
        }
        // 解析存储的字符串为无符号整数
        int value = tranNum(tokenStr);
        // 解析成功则返回无符号整数类型的token，否则返回编译错误
        // Token 的 Value 应填写数字的值
        if(value>=0){
            Token token= new Token(TokenType.Uint,value,startPos,it.currentPos());
            return token;
        }

        throw new Error("Compile Error");
    }

    private int tranNum(StringBuffer token) {
        int len = token.length();
        int i;
        for (i = 0; i < len; i++) {
            if (token.charAt(i) != '0') {
                token.delete(0, i);//去掉前置0
                break;
            }
        }
        if (i == len) {
            return 0;
        }
        return Integer.parseInt(token.toString());
    }

    private Token lexIdentOrKeyword() throws TokenizeError {
        // 请填空：
        char peek = it.peekChar();
        StringBuffer tokenStr = new StringBuffer();//单词的字符串
        Pos startPos = it.currentPos();//记录Pos初始位置
        Token token;
        // 直到查看下一个字符不是数字或字母为止:
        while(Character.isLetter(peek)||Character.isLetter(peek)){
            // -- 前进一个字符，并存储这个字符
//            System.out.print(it.nextChar());
            tokenStr.append(it.nextChar());
            peek = it.peekChar();
        }
        //
        // 尝试将存储的字符串解释为关键字
        // -- 如果是关键字，则返回关键字类型的 token
        token = Reserve(tokenStr,startPos,it.currentPos());
        // -- 否则，返回标识符
        //
        // Token 的 Value 应填写标识符或关键字的字符串
        if(token == null){
            token = new Token(TokenType.Ident,tokenStr.toString(),startPos,it.currentPos());
        }
        return token;
    }

    private Token Reserve(StringBuffer tokenStr,Pos startPos,Pos endPos) {
        String str = tokenStr.toString();
        switch (tokenStr.toString()){
            case "Begin":
                return new Token(TokenType.Begin,str,startPos,endPos);
            case "End":
                return new Token(TokenType.End,str,startPos,endPos);
            case "Var":
                return new Token(TokenType.Var,str,startPos,endPos);
            case "Const":
                return new Token(TokenType.Const,str,startPos,endPos);
            case "Print":
                return new Token(TokenType.Print,str,startPos,endPos);
            default:
                return null;
        }

    }

    private Token lexOperatorOrUnknown() throws TokenizeError {
        switch (it.nextChar()) {
            case '+':
                return new Token(TokenType.Plus, '+', it.previousPos(), it.currentPos());

            case '-':
                // 填入返回语句
                return new Token(TokenType.Minus, '-', it.previousPos(), it.currentPos());

            case '*':
                // 填入返回语句
                return new Token(TokenType.Mult, '*', it.previousPos(), it.currentPos());

            case '/':
                // 填入返回语句
                return new Token(TokenType.Div, '-', it.previousPos(), it.currentPos());

            // 填入更多状态和返回语句
            case '=':
                // 填入返回语句
                return new Token(TokenType.Equal, '=', it.previousPos(), it.currentPos());

            case ';':
                // 填入返回语句
                return new Token(TokenType.Semicolon, ';', it.previousPos(), it.currentPos());

            case '(':
                // 填入返回语句
                return new Token(TokenType.LParen, '(', it.previousPos(), it.currentPos());

            case ')':
                // 填入返回语句
                return new Token(TokenType.RParen, ')', it.previousPos(), it.currentPos());

            default:
                // 不认识这个输入，摸了
                throw new TokenizeError(ErrorCode.InvalidInput, it.previousPos());
        }
    }

    private void skipSpaceCharacters() {
        while (!it.isEOF() && Character.isWhitespace(it.peekChar())) {
            it.nextChar();
        }
    }
}
