package edu.uta.nlp.constant;

import edu.mit.jwi.item.POS;

/**
 * @author hxy
 */
public enum SynsetType {


    /**
     TAG_NOUN
     */
    TN(POS.NUM_NOUN),

    /**
     TAG_VERB
     */
    TV(POS.NUM_VERB),

    /**
     TAG_ADJECTIVE
     */
    TA(POS.NUM_ADJECTIVE),

    /**
     TAG_ADVERB
     */
    TR(POS.NUM_ADVERB),

    /**
     TAG_ADJECTIVE_SATELLITE
     */
    TS(POS.NUM_ADJECTIVE_SATELLITE),

    /**
     TAG_OTHER
     */
    OTHER(0);

    private Integer value;

    SynsetType(Integer value) {
        this.value = value;
    }

    public Integer SynsetType() {
        return value;
    }

    public static String getTag(Integer value) {

        switch (value) {
            case 1:return TN.toString();
            case 2:return TV.toString();
            case 3:return TA.toString();
            case 4:return TR.toString();
            case 5:return TS.toString();
            default:return OTHER.toString();
        }

    }
}
