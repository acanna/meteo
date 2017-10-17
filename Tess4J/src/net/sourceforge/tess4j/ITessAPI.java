/**
 * Copyright @ 2014 Quan Nguyen
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.sourceforge.tess4j;

import com.sun.jna.*;

import java.util.Arrays;
import java.util.List;

/**
 * An interface represents common TessAPI classes/constants.
 */
public interface ITessAPI {

    int TRUE = 1;
    int FALSE = 0;

    /**
     * When Tesseract/Cube is initialized we can choose to instantiate/load/run
     * only the Tesseract part, only the Cube part or both along with the
     * combiner. The preference of which engine to use is stored in
     * <code>tessedit_ocr_engine_mode</code>.<br>
     * <br>
     * ATTENTION: When modifying this enum, please make sure to make the
     * appropriate changes to all the enums mirroring it (e.g. OCREngine in
     * cityblock/workflow/detection/detection_storage.proto). Such enums will
     * mention the connection to OcrEngineMode in the comments.
     */
    interface TessOcrEngineMode {

        /**
         * Run Tesseract only - fastest
         */
        int OEM_TESSERACT_ONLY = 0;
        /**
         * Run Cube only - better accuracy, but slower
         */
        int OEM_CUBE_ONLY = 1;
        /**
         * Run both and combine results - best accuracy
         */
        int OEM_TESSERACT_CUBE_COMBINED = 2;
        /**
         * Specify this mode when calling <code>init_*()</code>, to indicate
         * that any of the above modes should be automatically inferred from the
         * variables in the language-specific config, command-line configs, or
         * if not specified in any of the above should be set to the default
         * <code>OEM_TESSERACT_ONLY</code>.
         */
        int OEM_DEFAULT = 3;
    }

    /**
     * Possible modes for page layout analysis. These *must* be kept in order of
     * decreasing amount of layout analysis to be done, except for
     * <code>OSD_ONLY</code>, so that the inequality test macros below work.
     */
    interface TessPageSegMode {

        /**
         * Orientation and script detection only.
         */
        int PSM_OSD_ONLY = 0;
        /**
         * Automatic page segmentation with orientation and script detection.
         * (OSD)
         */
        int PSM_AUTO_OSD = 1;
        /**
         * Automatic page segmentation, but no OSD, or OCR.
         */
        int PSM_AUTO_ONLY = 2;
        /**
         * Fully automatic page segmentation, but no OSD.
         */
        int PSM_AUTO = 3;
        /**
         * Assume a single column of text of variable sizes.
         */
        int PSM_SINGLE_COLUMN = 4;
        /**
         * Assume a single uniform block of vertically aligned text.
         */
        int PSM_SINGLE_BLOCK_VERT_TEXT = 5;
        /**
         * Assume a single uniform block of text.
         */
        int PSM_SINGLE_BLOCK = 6;
        /**
         * Treat the image as a single text line.
         */
        int PSM_SINGLE_LINE = 7;
        /**
         * Treat the image as a single word.
         */
        int PSM_SINGLE_WORD = 8;
        /**
         * Treat the image as a single word in a circle.
         */
        int PSM_CIRCLE_WORD = 9;
        /**
         * Treat the image as a single character.
         */
        int PSM_SINGLE_CHAR = 10;
        /**
         * Find as much text as possible in no particular order.
         */
        int PSM_SPARSE_TEXT = 11;
        /**
         * Sparse text with orientation and script detection.
         */
        int PSM_SPARSE_TEXT_OSD = 12;
        /**
         * Number of enum entries.
         */
        int PSM_COUNT = 13;
    }

    /**
     * Enum of the elements of the page hierarchy, used in
     * <code>ResultIterator</code> to provide functions that operate on each
     * level without having to have 5x as many functions.
     */
    interface TessPageIteratorLevel {

        /**
         * Block of text/image/separator line.
         */
        int RIL_BLOCK = 0;
        /**
         * Paragraph within a block.
         */
        int RIL_PARA = 1;
        /**
         * Line within a paragraph.
         */
        int RIL_TEXTLINE = 2;
        /**
         * Word within a textline.
         */
        int RIL_WORD = 3;
        /**
         * Symbol/character within a word.
         */
        int RIL_SYMBOL = 4;
    }

    /**
     * Possible types for a POLY_BLOCK or ColPartition. Must be kept in sync
     * with <code>kPBColors</code> in polyblk.cpp and <code>PTIs*Type</code>
     * functions below, as well as <code>kPolyBlockNames</code> in
     * publictypes.cpp. Used extensively by ColPartition, and POLY_BLOCK.
     */
    interface TessPolyBlockType {

        /**
         * Type is not yet known. Keep as the first element.
         */
        int PT_UNKNOWN = 0;
        /**
         * Text that lives inside a column.
         */
        int PT_FLOWING_TEXT = 1;
        /**
         * Text that spans more than one column.
         */
        int PT_HEADING_TEXT = 2;
        /**
         * Text that is in a cross-column pull-out region.
         */
        int PT_PULLOUT_TEXT = 3;
        /**
         * Partition belonging to an equation region.
         */
        int PT_EQUATION = 4;
        /**
         * Partition has inline equation.
         */
        int PT_INLINE_EQUATION = 5;
        /**
         * Partition belonging to a table region.
         */
        int PT_TABLE = 6;
        /**
         * Text-line runs vertically.
         */
        int PT_VERTICAL_TEXT = 7;
        /**
         * Text that belongs to an image.
         */
        int PT_CAPTION_TEXT = 8;
        /**
         * Image that lives inside a column.
         */
        int PT_FLOWING_IMAGE = 9;
        /**
         * Image that spans more than one column.
         */
        int PT_HEADING_IMAGE = 10;
        /**
         * Image that is in a cross-column pull-out region.
         */
        int PT_PULLOUT_IMAGE = 11;
        /**
         * Horizontal Line.
         */
        int PT_HORZ_LINE = 12;
        /**
         * Vertical Line.
         */
        int PT_VERT_LINE = 13;
        /**
         * Lies outside of any column.
         */
        int PT_NOISE = 14;
        /**
         * Number of enum entries.
         */
        int PT_COUNT = 15;
    }

    /**
     * NOTA BENE: Fully justified paragraphs (text aligned to both left and
     * right margins) are marked by Tesseract with JUSTIFICATION_LEFT if their
     * text is written with a left-to-right script and with JUSTIFICATION_RIGHT
     * if their text is written in a right-to-left script.<br>
     * <br>
     * Interpretation for text read in vertical lines: "Left" is wherever the
     * starting reading position is.
     */
    interface TessParagraphJustification {

        /**
         * The alignment is not clearly one of the other options. This could
         * happen for example if there are only one or two lines of text or the
         * text looks like source code or poetry.
         */
        int JUSTIFICATION_UNKNOWN = 0;
        /**
         * Each line, except possibly the first, is flush to the same left tab
         * stop.
         */
        int JUSTIFICATION_LEFT = 1;
        /**
         * The text lines of the paragraph are centered about a line going down
         * through their middle of the text lines.
         */
        int JUSTIFICATION_CENTER = 2;
        /**
         * Each line, except possibly the first, is flush to the same right tab
         * stop.
         */
        int JUSTIFICATION_RIGHT = 3;
    }

    /**
     * <pre>
     *  +------------------+
     *  | 1 Aaaa Aaaa Aaaa |
     *  | Aaa aa aaa aa    |
     *  | aaaaaa A aa aaa. |
     *  |                2 |
     *  |   #######  c c C |
     *  |   #######  c c c |
     *  | &lt; #######  c c c |
     *  | &lt; #######  c   c |
     *  | &lt; #######  .   c |
     *  | 3 #######      c |
     *  +------------------+
     * </pre> Orientation Example:
     * <br>
     * ====================
     * <br>
     * Above is a diagram of some (1) English and (2) Chinese text and a (3)
     * photo credit.<br>
     * <br>
     * Upright Latin characters are represented as A and a. '&lt;' represents a
     * latin character rotated anti-clockwise 90 degrees. Upright Chinese
     * characters are represented C and c.<br>
     * <br> NOTA BENE: enum values here should match goodoc.proto<br>
     * <br> If you orient your head so that "up" aligns with Orientation, then
     * the characters will appear "right side up" and readable.<br>
     * <br>
     * In the example above, both the English and Chinese paragraphs are
     * oriented so their "up" is the top of the page (page up). The photo credit
     * is read with one's head turned leftward ("up" is to page left).<br>
     * <br>
     * The values of this enum match the convention of Tesseract's osdetect.h
     */
    interface TessOrientation {

        int ORIENTATION_PAGE_UP = 0;
        int ORIENTATION_PAGE_RIGHT = 1;
        int ORIENTATION_PAGE_DOWN = 2;
        int ORIENTATION_PAGE_LEFT = 3;
    }

    /**
     * The grapheme clusters within a line of text are laid out logically in
     * this direction, judged when looking at the text line rotated so that its
     * Orientation is "page up".<br>
     * <br>
     * For English text, the writing direction is left-to-right. For the Chinese
     * text in the above example, the writing direction is top-to-bottom.
     */
    interface TessWritingDirection {

        int WRITING_DIRECTION_LEFT_TO_RIGHT = 0;
        int WRITING_DIRECTION_RIGHT_TO_LEFT = 1;
        int WRITING_DIRECTION_TOP_TO_BOTTOM = 2;
    }

    /**
     * The text lines are read in the given sequence.<br>
     * <br>
     * In English, the order is top-to-bottom. In Chinese, vertical text lines
     * are read right-to-left. Mongolian is written in vertical columns top to
     * bottom like Chinese, but the lines order left-to right.<br>
     * <br>
     * Note that only some combinations make sense. For example,
     * <code>WRITING_DIRECTION_LEFT_TO_RIGHT</code> implies
     * <code>TEXTLINE_ORDER_TOP_TO_BOTTOM</code>.
     */
    interface TessTextlineOrder {

        int TEXTLINE_ORDER_LEFT_TO_RIGHT = 0;
        int TEXTLINE_ORDER_RIGHT_TO_LEFT = 1;
        int TEXTLINE_ORDER_TOP_TO_BOTTOM = 2;
    }

    /**
     * Callback for <code>cancel_func</code>.
     */
    interface CANCEL_FUNC extends Callback {

        /**
         * @param cancel_this
         * @param words
         * @return
         */
        boolean invoke(Pointer cancel_this, int words);
    }

    /**
     * Base class for all tesseract APIs. Specific classes can add ability to
     * work on different inputs or produce different outputs. This class is
     * mostly an interface layer on top of the Tesseract instance class to hide
     * the data types so that users of this class don't have to include any
     * other Tesseract headers.
     */
    class TessBaseAPI extends PointerType {

        public TessBaseAPI(Pointer address) {
            super(address);
        }

        public TessBaseAPI() {
            super();
        }
    }

    /**
     * Class to iterate over tesseract page structure, providing access to all
     * levels of the page hierarchy, without including any tesseract headers or
     * having to handle any tesseract structures.<br>
     * WARNING! This class points to data held within the TessBaseAPI class, and
     * therefore can only be used while the TessBaseAPI class still exists and
     * has not been subjected to a call of <code>Init</code>,
     * <code>SetImage</code>, <code>Recognize</code>, <code>Clear</code>,
     * <code>End</code> <code>DetectOS</code>, or anything else that changes the
     * internal <code>PAGE_RES</code>. See <code>apitypes.h</code> for the
     * definition of <code>PageIteratorLevel</code>. See also
     * <code>ResultIterator</code>, derived from <code>PageIterator</code>,
     * which adds in the ability to access OCR output with text-specific
     * methods.
     */
    class TessPageIterator extends PointerType {

        public TessPageIterator(Pointer address) {
            super(address);
        }

        public TessPageIterator() {
            super();
        }
    }

    /**
     * MutableIterator adds access to internal data structures.
     */
    class TessMutableIterator extends PointerType {

        public TessMutableIterator(Pointer address) {
            super(address);
        }

        public TessMutableIterator() {
            super();
        }
    }

    /**
     * Iterator for tesseract results that is capable of iterating in proper
     * reading order over Bi Directional (e.g. mixed Hebrew and English) text.
     * ResultIterator adds text-specific methods for access to OCR output.
     */
    class TessResultIterator extends PointerType {

        public TessResultIterator(Pointer address) {
            super(address);
        }

        public TessResultIterator() {
            super();
        }
    }

    class TessChoiceIterator extends PointerType {

        public TessChoiceIterator(Pointer address) {
            super(address);
        }

        public TessChoiceIterator() {
            super();
        }
    }

    /**
     * Interface for rendering tesseract results into a document, such as text,
     * HOCR or pdf. This class is abstract. Specific classes handle individual
     * formats. This interface is then used to inject the renderer class into
     * tesseract when processing images.
     * <p>
     * For simplicity implementing this with tesseract version 3.01, the
     * renderer contains document state that is cleared from document to
     * document just as the TessBaseAPI is. This way the base API can just
     * delegate its rendering functionality to injected renderers, and the
     * renderers can manage the associated state needed for the specific formats
     * in addition to the heuristics for producing it.
     */
    class TessResultRenderer extends PointerType {

        public TessResultRenderer(Pointer address) {
            super(address);
        }

        public TessResultRenderer() {
            super();
        }
    }

    /**
     * Description of the output of the OCR engine. This structure is used as
     * both a progress monitor and the final output header, since it needs to be
     * a valid progress monitor while the OCR engine is storing its output to
     * shared memory. During progress, all the buffer info is -1. Progress
     * starts at 0 and increases to 100 during OCR. No other constraint. Every
     * progress callback, the OCR engine must set <code>ocr_alive</code> to 1.
     * The HP side will set <code>ocr_alive</code> to 0. Repeated failure to
     * reset to 1 indicates that the OCR engine is dead. If the cancel function
     * is not null then it is called with the number of user words found. If it
     * returns true then operation is cancelled.
     */
    class ETEXT_DESC extends Structure {

        /**
         * chars in this buffer(0). Total number of UTF-8 bytes for this run.
         */
        public short count;
        /**
         * percent complete increasing (0-100)
         */
        public short progress;
        /**
         * true if not last
         */
        public byte more_to_come;
        /**
         * ocr sets to 1, HP 0
         */
        public byte ocr_alive;
        /**
         * for errcode use
         */
        public byte err_code;
        /**
         * returns true to cancel
         */
        public CANCEL_FUNC cancel;
        /**
         * this or other data for cancel
         */
        public Pointer cancel_this;
        /**
         * time to stop if not 0
         */
        public TimeVal end_time;
        /**
         * character data
         */
        public EANYCODE_CHAR[] text = new EANYCODE_CHAR[1];

        /**
         * Gets Field Order.
         *
         * @return
         */
        @Override
        protected List getFieldOrder() {
            return Arrays.asList("count", "progress", "more_to_come", "ocr_alive", "err_code", "cancel", "cancel_this", "end_time", "text");
        }
    }

    /**
     * It should be noted that the format for char_code for version 2.0 and
     * beyond is UTF-8, which means that ASCII characters will come out as one
     * structure but other characters will be returned in two or more instances
     * of this structure with a single byte of the UTF-8 code in each, but each
     * will have the same bounding box.<br>
     * <br>
     * Programs which want to handle languages with different characters sets
     * will need to handle extended characters appropriately, but
     * <strong>all</strong>
     * code needs to be prepared to receive UTF-8 coded characters for
     * characters such as bullet and fancy quotes.
     */
    class EANYCODE_CHAR extends Structure {

        /**
         * character itself, one single UTF-8 byte long. A Unicode character may
         * consist of one or more UTF-8 bytes. Bytes of a character will have
         * the same bounding box.
         */
        public byte char_code;
        /**
         * left of char (-1)
         */
        public short left;
        /**
         * right of char (-1)
         */
        public short right;
        /**
         * top of char (-1)
         */
        public short top;
        /**
         * bottom of char (-1)
         */
        public short bottom;
        /**
         * what font (0)
         */
        public short font_index;
        /**
         * classification confidence: 0=perfect, 100=reject (0/100)
         */
        public byte confidence;
        /**
         * point size of char, 72 = 1 inch, (10)
         */
        public byte point_size;
        /**
         * number of spaces before this char (1)
         */
        public byte blanks;
        /**
         * char formatting (0)
         */
        public byte formatting;

        /**
         * Gets Field Order.
         *
         * @return
         */
        @Override
        protected List getFieldOrder() {
            return Arrays.asList("char_code", "left", "right", "top", "bottom", "font_index", "confidence", "point_size", "blanks", "formatting");
        }
    }

    class TimeVal extends Structure {

        /**
         * seconds
         */
        public NativeLong tv_sec;
        /**
         * microseconds
         */
        public NativeLong tv_usec;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("tv_sec", "tv_usec");
        }
    }
}
