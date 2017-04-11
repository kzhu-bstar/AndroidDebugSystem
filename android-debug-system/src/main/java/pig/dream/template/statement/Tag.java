package pig.dream.template.statement;

import java.util.ArrayList;
import java.util.List;

import static pig.dream.template.statement.StatementErrorException.NotSupportStatement;

/**
 * Created by zhukun on 2017/4/11.
 */

public class Tag {
    private final static List<Tag> tags = new ArrayList<>();

    static {
        tags.add(new Tag("if", Tag.BEGIN, Statement.TYPE.IF));
        tags.add(new Tag("elseif", Tag.CENTRE, Statement.TYPE.IF));
        tags.add(new Tag("endif", Tag.END, Statement.TYPE.IF));
//        tags.add(new Tag("for", Tag.BEGIN, Statement.TYPE.FOR));
//        tags.add(new Tag("endfor", Tag.END, Statement.TYPE.FOR));
    }

    public final static int BEGIN = 0;
    public final static int CENTRE = 1;
    public final static int END = 2;

    public String tag;
    public int position;
    public int type;

    private Tag(String tag, int position, int type) {
        this.tag = tag;
        this.position = position;
        this.type = type;
    }

    public static Tag createByTagName(String name) {
        for (Tag tag : tags) {
            if (name.startsWith(tag.tag)) {
                return tag;
            }
        }

        throw new StatementErrorException(NotSupportStatement, name);
    }

}
