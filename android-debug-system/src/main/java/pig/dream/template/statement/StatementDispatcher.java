package pig.dream.template.statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhukun on 2017/4/11.
 */

public class StatementDispatcher {

    private Stack<Statement> statementStack;
    private List<Link> links;
    private int lastIndex;

    public StatementDispatcher() {
        statementStack = new Stack<>();
        links = new ArrayList<>();
    }

    public void pretreat(String result) {
        Pattern pattern = Pattern.compile("<%(.+?)%>");
        Matcher matcher = pattern.matcher(result);
        while (matcher.find()) {
            String name = matcher.group(1).trim();// 键名

            Tag tag = Tag.createByTagName(name);
            if (tag.position == Tag.BEGIN) {
                Statement statement = StatementFactory.createStatement(tag.type, result);
                statement.start(name, matcher, lastIndex);
                push(statement);
            } else if (tag.position == Tag.END) {
                lastIndex = matcher.end();
                Statement statement = statementStack.pop();
                boolean checkStatementTypeResult = StatementFactory.checkStatementType(statement, tag.type);
                if (checkStatementTypeResult) {
                    statement.end(matcher);
                    pop(statement);
                } else {
                    throw new StatementErrorException(StatementErrorException.Mismatch);
                }
            } else {
                // CENTRE
            }
        }
    }

    public String dispatcher(String result, Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();

        for (Link link:links) {
            Statement statement = link.statement;
            statement.append(sb, data);
        }

        sb.append(result.substring(lastIndex, result.length()));
        return sb.toString();
    }

    private void push(Statement statement) {
        statementStack.push(statement);
    }

    private void pop(Statement statement) {
        Link link = new Link();
        link.statement = statement;
        int size = statementStack.size();
        if (size == 1) {
            links.add(link);
        } else {
            Link tempLink = links.get(links.size() - 1);
            for (int i = 0; i < size - 2; i++) {
                List<Link> list = tempLink.nexts;
                tempLink = list.get(list.size() - 1);
            }
            if (tempLink.nexts == null) {
                tempLink.nexts = new ArrayList<>();
            }
            tempLink.nexts.add(link);
        }
    }

    public static class Link {
        public Statement statement;
        public List<Link> nexts;
    }
}
