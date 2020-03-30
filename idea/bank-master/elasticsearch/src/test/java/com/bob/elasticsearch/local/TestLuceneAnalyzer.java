package com.bob.elasticsearch.local;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;

/**
 * @author Administrator
 * @create 2019-05-12 22:49
 */
public class TestLuceneAnalyzer {

    private void print(Analyzer analyzer) throws Exception {
        String text = "Lucene自带多种分词器，其中对中文分词支持比较好的是smartcn。";
        TokenStream tokenStream = analyzer.tokenStream("content", text);
        CharTermAttribute attribute = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            System.out.println(attribute.toString());
        }
    }

    /**
     * 标准分词器
     *
     * @throws Exception
     */
    @Test
    public void testStandardAnalyzer() throws Exception {
        print(new StandardAnalyzer());
    }


}



