package com.shetuan.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/1 21:11
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
public class SQLParser {
    private StringBuffer sqlstr = new StringBuffer();
    private HashMap<String,Object> param;

    public SQLParser(HashMap<String,Object> param) throws Exception {
        this.param = param;
    }

    private List getTokens(String sql) throws Exception {
        Pattern patParam = Pattern.compile("(:[\\w]*)");
        Pattern patQuote = Pattern.compile("('[^']*')");
        List quoteRanges = new ArrayList();
        Matcher matcher = patQuote.matcher(sql);

        class QuoteRange {
            int start;
            int length;
            String text;

            QuoteRange() {
            }
        }

        while(matcher.find()) {
            QuoteRange r = new QuoteRange();
            r.start = matcher.start();
            r.text = matcher.group();
            r.length = r.text.length();
            quoteRanges.add(r);
        }

        matcher = patParam.matcher(sql);
        ArrayList keys = new ArrayList();

        while(true) {
            String key;
            boolean skip;
            do {
                if (!matcher.find()) {
                    return keys;
                }

                key = matcher.group().substring(1);
                if (quoteRanges.isEmpty()) {
                    break;
                }

                skip = false;
                int pos = matcher.start();
                Iterator it = quoteRanges.iterator();

                while(it.hasNext()) {
                    QuoteRange r = (QuoteRange)it.next();
                    if (pos >= r.start && pos < r.start + r.length) {
                        skip = true;
                        break;
                    }
                }
            } while(skip);

            keys.add(key);
        }
    }

    public void addSQL(String sql) throws Exception {
        List names = this.getTokens(sql);
        if (names.isEmpty()) {
            this.sqlstr.append(sql);
        } else {
            int i = 0;

            while(true) {
                if (i >= names.size()) {
                    this.sqlstr.append(sql);
                    break;
                }

                String name = (String)names.get(i);
                String value = (String)this.param.get(name);
                if (value == null || "".equals(value.trim())) {
                    return;
                }

                ++i;
            }
        }

    }

    public String getSQL() throws Exception {
        return this.sqlstr.toString();
    }

    public HashMap<String,Object> getParam() throws Exception {
        return this.param;
    }

    public void addParser(SQLParser parser) throws Exception {
        this.param.putAll(parser.getParam());
        this.sqlstr.append(parser.getSQL());
    }
}
