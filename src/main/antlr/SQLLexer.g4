lexer grammar SQLLexer;

// ****************************************************************************
// II Universal Lexer
// ****************************************************************************

//-----
// SKIP
//-----
SPACE:                          [ \t\r\n]+    -> channel(HIDDEN);
COMMENT_INPUT:                  '/*' .*? '*/' -> channel(HIDDEN);
//REMARK: if only one symbol '\r' in query. Is this error?
LINE_COMMENT:                   ('-- ' | '#') ~[\r\n]* '\r'? '\n' -> channel(HIDDEN);

//---------
// for debug
//---------
DUMMY:                          D U M M Y;


//---------
//---------
// Keywords
//---------
//---------


ABS:                        A B S;
ABSOLUTE:                       A B S O L U T E;
ACOS:                       A C O S;
ACTION:                     A C T I O N;
ADD:                        A D D;
ADDDATE:                        A D D D A T E;
ADDTIME:                        A D D T I M E;
AES_DECRYPT:                        A E S '_' D E C R Y P T;
AES_ENCRYPT:                        A E S '_' E N C R Y P T;
AFTER:                      A F T E R;
AGENT:                      A G E N T;
AGGREGATE:                      A G G R E G A T E;
ALGORITHM:                      A L G O R I T H M;
ALL:                        A L L;
ALLOWED:                        A L L O W E D;
ALLOW_SNAPSHOT_ISOLATION:                       A L L O W '_' S N A P S H O T '_' I S O L A T I O N;
ALTER:                      A L T E R;
ANALYZE:                        A N A L Y Z E;
AND:                        A N D;
ANSI_NULLS:                     A N S I '_' N U L L S;
ANSI_NULL_DEFAULT:                      A N S I '_' N U L L '_' D E F A U L T;
ANSI_PADDING:                       A N S I '_' P A D D I N G;
ANSI_WARNINGS:                      A N S I '_' W A R N I N G S;
ANY:                        A N Y;
APPLY:                      A P P L Y;
ARCHIVE:                        A R C H I V E;
AREA:                       A R E A;
ARITHABORT:                     A R I T H A B O R T;
ARMSCII8:                       A R M S C I I '8';
ARRAY:                      A R R A Y;
AS:                     A S;
ASBINARY:                       A S B I N A R Y;
ASC:                        A S C;
ASCII:                      A S C I I;
ASIN:                       A S I N;
ASSOCIATE:                      A S S O C I A T E;
ASTEXT:                     A S T E X T;
ASWKB:                      A S W K B;
ASWKT:                      A S W K T;
ASYMMETRIC_DECRYPT:                     A S Y M M E T R I C '_' D E C R Y P T;
ASYMMETRIC_DERIVE:                      A S Y M M E T R I C '_' D E R I V E;
ASYMMETRIC_ENCRYPT:                     A S Y M M E T R I C '_' E N C R Y P T;
ASYMMETRIC_SIGN:                        A S Y M M E T R I C '_' S I G N;
ASYMMETRIC_VERIFY:                      A S Y M M E T R I C '_' V E R I F Y;
AT:                     A T;
ATAN:                       A T A N;
ATAN2:                      A T A N '2';
ATTRIBUTE:                      A T T R I B U T E;
AUDIT:                      A U D I T;
AUTHID:                     A U T H I D;
AUTHORIZATION:                      A U T H O R I Z A T I O N;
AUTHORS:                        A U T H O R S;
AUTO:                       A U T O;
AUTOCOMMIT:                     A U T O C O M M I T;
AUTOEXTEND_SIZE:                        A U T O E X T E N D '_' S I Z E;
AUTOMATIC:                      A U T O M A T I C;
AUTONOMOUS_TRANSACTION:                     A U T O N O M O U S '_' T R A N S A C T I O N;
AUTO_CLEANUP:                       A U T O '_' C L E A N U P;
AUTO_CLOSE:                     A U T O '_' C L O S E;
AUTO_CREATE_STATISTICS:                     A U T O '_' C R E A T E '_' S T A T I S T I C S;
AUTO_INCREMENT:                     A U T O '_' I N C R E M E N T;
AUTO_SHRINK:                        A U T O '_' S H R I N K;
AUTO_UPDATE_STATISTICS:                     A U T O '_' U P D A T E '_' S T A T I S T I C S;
AUTO_UPDATE_STATISTICS_ASYNC:                       A U T O '_' U P D A T E '_' S T A T I S T I C S '_' A S Y N C;
AVG:                        A V G;
AVG_ROW_LENGTH:                     A V G '_' R O W '_' L E N G T H;
BACKUP:                     B A C K U P;
BASE64:                     B A S E '6' '4';
BATCH:                      B A T C H;
BEFORE:                     B E F O R E;
BEGIN:                      B E G I N;
BENCHMARK:                      B E N C H M A R K;
BETWEEN:                        B E T W E E N;
BFILE:                      B F I L E;
BIG5:                       B I G '5';
BIGINT:                     B I G I N T;
BIN:                        B I N;
BINARY:                     B I N A R Y;
BINARY_CHECKSUM:                        B I N A R Y '_' C H E C K S U M;
BINARY_DOUBLE:                      B I N A R Y '_' D O U B L E;
BINARY_FLOAT:                       B I N A R Y '_' F L O A T;
BINARY_INTEGER:                     B I N A R Y '_' I N T E G E R;
BINLOG:                     B I N L O G;
BIT:                        B I T;
BIT_AND:                        B I T '_' A N D;
BIT_COUNT:                      B I T '_' C O U N T;
BIT_LENGTH:                     B I T '_' L E N G T H;
BIT_OR:                     B I T '_' O R;
BIT_XOR:                        B I T '_' X O R;
BLACKHOLE:                      B L A C K H O L E;
BLOB:                       B L O B;
BLOCK:                      B L O C K;
BODY:                       B O D Y;
BOOLEAN:                        B O O L E A N;
BOTH:                       B O T H;
BREADTH:                        B R E A D T H;
BREAK:                      B R E A K;
BROWSE:                     B R O W S E;
BTREE:                      B T R E E;
BUFFER:                     B U F F E R;
BULK:                       B U L K;
BULK_LOGGED:                        B U L K '_' L O G G E D;
BY:                     B Y;
BYTE:                       B Y T E;
CACHE:                      C A C H E;
CALL:                       C A L L;
CALLER:                     C A L L E R;
CANONICAL:                      C A N O N I C A L;
CASCADE:                        C A S C A D E;
CASCADED:                       C A S C A D E D;
CASE:                       C A S E;
CAST:                       C A S T;
CATCH:                      C A T C H;
CEIL:                       C E I L;
CEILING:                        C E I L I N G;
CENTROID:                       C E N T R O I D;
CHAIN:                      C H A I N;
CHANGE:                     C H A N G E;
CHANGED:                        C H A N G E D;
CHANGES:                        C H A N G E S;
CHANGETABLE:                        C H A N G E T A B L E;
CHANGE_RETENTION:                       C H A N G E '_' R E T E N T I O N;
CHANGE_TRACKING:                        C H A N G E '_' T R A C K I N G;
CHAR:                       C H A R;
CHARACTER:                      C H A R A C T E R;
CHARACTER_LENGTH:                       C H A R A C T E R '_' L E N G T H;
CHARSET:                        C H A R S E T;
CHAR_CS:                        C H A R '_' C S;
CHAR_LENGTH:                        C H A R '_' L E N G T H;
CHECK:                      C H E C K;
CHECKPOINT:                     C H E C K P O I N T;
CHECKSUM:                       C H E C K S U M;
CHECKSUM_AGG:                       C H E C K S U M '_' A G G;
CHR:                        C H R;
CIPHER:                     C I P H E R;
CLIENT:                     C L I E N T;
CLOB:                       C L O B;
CLOSE:                      C L O S E;
CLUSTER:                        C L U S T E R;
CLUSTERED:                      C L U S T E R E D;
COALESCE:                       C O A L E S C E;
CODE:                       C O D E;
COERCIBILITY:                       C O E R C I B I L I T Y;
COLLATE:                        C O L L A T E;
COLLATION:                      C O L L A T I O N;
COLLECT:                        C O L L E C T;
COLUMN:                     C O L U M N;
COLUMNS:                        C O L U M N S;
COLUMN_FORMAT:                      C O L U M N '_' F O R M A T;
COMMENT:                        C O M M E N T;
COMMIT:                     C O M M I T;
COMMITTED:                      C O M M I T T E D;
COMPACT:                        C O M P A C T;
COMPATIBILITY:                      C O M P A T I B I L I T Y;
COMPATIBILITY_LEVEL:                        C O M P A T I B I L I T Y '_' L E V E L;
COMPILE:                        C O M P I L E;
COMPLETION:                     C O M P L E T I O N;
COMPOUND:                       C O M P O U N D;
COMPRESS:                       C O M P R E S S;
COMPRESSED:                     C O M P R E S S E D;
COMPUTE:                        C O M P U T E;
CONCAT:                     C O N C A T;
CONCAT_NULL_YIELDS_NULL:                        C O N C A T '_' N U L L '_' Y I E L D S '_' N U L L;
CONCAT_WS:                      C O N C A T '_' W S;
CONCURRENT:                     C O N C U R R E N T;
CONDITION:                      C O N D I T I O N;
CONNECT:                        C O N N E C T;
CONNECTION:                     C O N N E C T I O N;
CONNECTION_ID:                      C O N N E C T I O N '_' I D;
CONNECT_BY_ROOT:                        C O N N E C T '_' B Y '_' R O O T;
CONSISTENT:                     C O N S I S T E N T;
CONSTANT:                       C O N S T A N T;
CONSTRAINT:                     C O N S T R A I N T;
CONSTRAINTS:                        C O N S T R A I N T S;
CONSTRUCTOR:                        C O N S T R U C T O R;
CONTAINMENT:                        C O N T A I N M E N T;
CONTAINS:                       C O N T A I N S;
CONTAINSTABLE:                      C O N T A I N S T A B L E;
CONTENT:                        C O N T E N T;
CONTEXT:                        C O N T E X T;
CONTINUE:                       C O N T I N U E;
CONTRIBUTORS:                       C O N T R I B U T O R S;
CONTROL:                        C O N T R O L;
CONV:                       C O N V;
CONVERT:                        C O N V E R T;
CONVERT_TZ:                     C O N V E R T '_' T Z;
COOKIE:                     C O O K I E;
COPY:                       C O P Y;
CORR:                       C O R R;
CORRUPT_XID:                        C O R R U P T '_' X I D;
CORRUPT_XID_ALL:                        C O R R U P T '_' X I D '_' A L L;
COS:                        C O S;
COST:                       C O S T;
COT:                        C O T;
COUNT:                      C O U N T;
COUNT_BIG:                      C O U N T '_' B I G;
COVAR_:                     C O V A R '_';
CP1250:                     C P '1' '2' '5' '0';
CP1251:                     C P '1' '2' '5' '1';
CP1256:                     C P '1' '2' '5' '6';
CP1257:                     C P '1' '2' '5' '7';
CP850:                      C P '8' '5' '0';
CP852:                      C P '8' '5' '2';
CP866:                      C P '8' '6' '6';
CP932:                      C P '9' '3' '2';
CRC32:                      C R C '3' '2';
CREATE:                     C R E A T E;
CREATE_ASYMMETRIC_PRIV_KEY:                     C R E A T E '_' A S Y M M E T R I C '_' P R I V '_' K E Y;
CREATE_ASYMMETRIC_PUB_KEY:                      C R E A T E '_' A S Y M M E T R I C '_' P U B '_' K E Y;
CREATE_DH_PARAMETERS:                       C R E A T E '_' D H '_' P A R A M E T E R S;
CREATE_DIGEST:                      C R E A T E '_' D I G E S T;
CROSS:                      C R O S S;
CROSSES:                        C R O S S E S;
CSV:                        C S V;
CUBE:                       C U B E;
CUME_DIST:                      C U M E '_' D I S T;
CURDATE:                        C U R D A T E;
CURRENT:                        C U R R E N T;
CURRENT_DATE:                       C U R R E N T '_' D A T E;
CURRENT_TIME:                       C U R R E N T '_' T I M E;
CURRENT_TIMESTAMP:                      C U R R E N T '_' T I M E S T A M P;
CURRENT_USER:                       C U R R E N T '_' U S E R;
CURSOR:                     C U R S O R;
CURSOR_CLOSE_ON_COMMIT:                     C U R S O R '_' C L O S E '_' O N '_' C O M M I T;
CURSOR_DEFAULT:                     C U R S O R '_' D E F A U L T;
CURTIME:                        C U R T I M E;
CUSTOMDATUM:                        C U S T O M D A T U M;
CYCLE:                      C Y C L E;
C_LETTER:                       C '_' L E T T E R;
DATA:                       D A T A;
DATABASE:                       D A T A B A S E;
DATABASES:                      D A T A B A S E S;
DATAFILE:                       D A T A F I L E;
DATE:                       D A T E;
DATEADD:                        D A T E A D D;
DATEDIFF:                       D A T E D I F F;
DATENAME:                       D A T E N A M E;
DATEPART:                       D A T E P A R T;
DATETIME:                       D A T E T I M E;
DATE_ADD:                       D A T E '_' A D D;
DATE_CORRELATION_OPTIMIZATION:                      D A T E '_' C O R R E L A T I O N '_' O P T I M I Z A T I O N;
DATE_FORMAT:                        D A T E '_' F O R M A T;
DATE_SUB:                       D A T E '_' S U B;
DAY:                        D A Y;
DAYNAME:                        D A Y N A M E;
DAYOFMONTH:                     D A Y O F M O N T H;
DAYOFWEEK:                      D A Y O F W E E K;
DAYOFYEAR:                      D A Y O F Y E A R;
DAYS:                       D A Y S;
DAY_HOUR:                       D A Y '_' H O U R;
DAY_MICROSECOND:                        D A Y '_' M I C R O S E C O N D;
DAY_MINUTE:                     D A Y '_' M I N U T E;
DAY_SECOND:                     D A Y '_' S E C O N D;
DBCC:                       D B C C;
DBTIMEZONE:                     D B T I M E Z O N E;
DB_CHAINING:                        D B '_' C H A I N I N G;
DB_ROLE_CHANGE:                     D B '_' R O L E '_' C H A N G E;
DDL:                        D D L;
DEALLOCATE:                     D E A L L O C A T E;
DEBUG:                      D E B U G;
DEC:                        D E C;
DEC8:                       D E C '8';
DECIMAL:                        D E C I M A L;
DECLARE:                        D E C L A R E;
DECODE:                     D E C O D E;
DECOMPOSE:                      D E C O M P O S E;
DECREMENT:                      D E C R E M E N T;
DEFAULT:                        D E F A U L T;
DEFAULTS:                       D E F A U L T S;
DEFAULT_FULLTEXT_LANGUAGE:                      D E F A U L T '_' F U L L T E X T '_' L A N G U A G E;
DEFAULT_LANGUAGE:                       D E F A U L T '_' L A N G U A G E;
DEFERRED:                       D E F E R R E D;
DEFINER:                        D E F I N E R;
DEGREES:                        D E G R E E S;
DELAY:                      D E L A Y;
DELAYED:                        D E L A Y E D;
DELAYED_DURABILITY:                     D E L A Y E D '_' D U R A B I L I T Y;
DELAY_KEY_WRITE:                        D E L A Y '_' K E Y '_' W R I T E;
DELETE:                     D E L E T E;
DELETED:                        D E L E T E D;
DENSE_RANK:                     D E N S E '_' R A N K;
DENY:                       D E N Y;
DEPTH:                      D E P T H;
DESC:                       D E S C;
DESCRIBE:                       D E S C R I B E;
DES_DECRYPT:                        D E S '_' D E C R Y P T;
DES_ENCRYPT:                        D E S '_' E N C R Y P T;
DES_KEY_FILE:                       D E S '_' K E Y '_' F I L E;
DETERMINISTIC:                      D E T E R M I N I S T I C;
DIMENSION:                      D I M E N S I O N;
DIRECTORY:                      D I R E C T O R Y;
DIRECTORY_NAME:                     D I R E C T O R Y '_' N A M E;
DISABLE:                        D I S A B L E;
DISABLED:                       D I S A B L E D;
DISABLE_BROKER:                     D I S A B L E '_' B R O K E R;
DISASSOCIATE:                       D I S A S S O C I A T E;
DISCARD:                        D I S C A R D;
DISJOINT:                       D I S J O I N T;
DISK:                       D I S K;
DISTINCT:                       D I S T I N C T;
DISTINCTROW:                        D I S T I N C T R O W;
DISTRIBUTED:                        D I S T R I B U T E D;
DO:                     D O;
DOCUMENT:                       D O C U M E N T;
DOUBLE:                     D O U B L E;
DROP:                       D R O P;
DSINTERVAL_UNCONSTRAINED:                       D S I N T E R V A L '_' U N C O N S T R A I N E D;
DUMP:                       D U M P;
DUMPFILE:                       D U M P F I L E;
DUPLICATE:                      D U P L I C A T E;
DYNAMIC:                        D Y N A M I C;
EACH:                       E A C H;
ELEMENT:                        E L E M E N T;
ELSE:                       E L S E;
ELSEIF:                     E L S E I F;
ELSIF:                      E L S I F;
ELT:                        E L T;
EMERGENCY:                      E M E R G E N C Y;
EMPTY:                      E M P T Y;
ENABLE:                     E N A B L E;
ENABLE_BROKER:                      E N A B L E '_' B R O K E R;
ENCLOSED:                       E N C L O S E D;
ENCODE:                     E N C O D E;
ENCODING:                       E N C O D I N G;
ENCRYPT:                        E N C R Y P T;
ENCRYPTION:                     E N C R Y P T I O N;
END:                        E N D;
ENDPOINT:                       E N D P O I N T;
ENDS:                       E N D S;
ENGINE:                     E N G I N E;
ENGINES:                        E N G I N E S;
ENTITYESCAPING:                     E N T I T Y E S C A P I N G;
ENUM:                       E N U M;
ENVELOPE:                       E N V E L O P E;
EQUALS:                     E Q U A L S;
ERR:                        E R R;
ERRLVL:                     E R R L V L;
ERRORS:                     E R R O R S;
ERROR_BROKER_CONVERSATIONS:                     E R R O R '_' B R O K E R '_' C O N V E R S A T I O N S;
ESCAPE:                     E S C A P E;
ESCAPED:                        E S C A P E D;
EUCJPMS:                        E U C J P M S;
EUCKR:                      E U C K R;
EUR:                        E U R;
EVALNAME:                       E V A L N A M E;
EVEN:                       E V E N;
EVENT:                      E V E N T;
EVENTS:                     E V E N T S;
EVERY:                      E V E R Y;
EXCEPT:                     E X C E P T;
EXCEPTION:                      E X C E P T I O N;
EXCEPTIONS:                     E X C E P T I O N S;
EXCEPTION_INIT:                     E X C E P T I O N '_' I N I T;
EXCHANGE:                       E X C H A N G E;
EXCLUDE:                        E X C L U D E;
EXCLUSIVE:                      E X C L U S I V E;
EXECUTE:                        E X E C U T E;
EXISTS:                     E X I S T S;
EXIT:                       E X I T;
EXP:                        E X P;
EXPAND:                     E X P A N D;
EXPIRE:                     E X P I R E;
EXPLAIN:                        E X P L A I N;
EXPORT:                     E X P O R T;
EXPORT_SET:                     E X P O R T '_' S E T;
EXTENDED:                       E X T E N D E D;
EXTENT_SIZE:                        E X T E N T '_' S I Z E;
EXTERIORRING:                       E X T E R I O R R I N G;
EXTERNAL:                       E X T E R N A L;
EXTRACT:                        E X T R A C T;
EXTRACTVALUE:                       E X T R A C T V A L U E;
FAILURE:                        F A I L U R E;
FALSE:                      F A L S E;
FAST:                       F A S T;
FAST_FORWARD:                       F A S T '_' F O R W A R D;
FEDERATED:                      F E D E R A T E D;
FETCH:                      F E T C H;
FIELD:                      F I E L D;
FIELDS:                     F I E L D S;
FILE:                       F I L E;
FILEGROUP:                      F I L E G R O U P;
FILEGROWTH:                     F I L E G R O W T H;
FILENAME:                       F I L E N A M E;
FILESTREAM:                     F I L E S T R E A M;
FILLFACTOR:                     F I L L F A C T O R;
FINAL:                      F I N A L;
FIND_IN_SET:                        F I N D '_' I N '_' S E T;
FIRST:                      F I R S T;
FIRST_VALUE:                        F I R S T '_' V A L U E;
FIXED:                      F I X E D;
FLOAT:                      F L O A T;
FLOOR:                      F L O O R;
FLUSH:                      F L U S H;
FOLLOWING:                      F O L L O W I N G;
FOLLOWS:                        F O L L O W S;
FOR:                        F O R;
FORALL:                     F O R A L L;
FORCE:                      F O R C E;
FORCED:                     F O R C E D;
FORCESEEK:                      F O R C E S E E K;
FOREIGN:                        F O R E I G N;
FORMAT:                     F O R M A T;
FORWARD_ONLY:                       F O R W A R D '_' O N L Y;
FOUND:                      F O U N D;
FOUND_ROWS:                     F O U N D '_' R O W S;
FREETEXT:                       F R E E T E X T;
FREETEXTTABLE:                      F R E E T E X T T A B L E;
FROM:                       F R O M;
FROM_BASE64:                        F R O M '_' B A S E '6' '4';
FROM_DAYS:                      F R O M '_' D A Y S;
FROM_UNIXTIME:                      F R O M '_' U N I X T I M E;
FULL:                       F U L L;
FULLSCAN:                       F U L L S C A N;
FULLTEXT:                       F U L L T E X T;
FUNCTION:                       F U N C T I O N;
GB:                     G B;
GB2312:                     G B '2' '3' '1' '2';
GBK:                        G B K;
GENERAL:                        G E N E R A L;
GEOMCOLLFROMTEXT:                       G E O M C O L L F R O M T E X T;
GEOMCOLLFROMWKB:                        G E O M C O L L F R O M W K B;
GEOMETRYCOLLECTION:                     G E O M E T R Y C O L L E C T I O N;
GEOMETRYCOLLECTIONFROMTEXT:                     G E O M E T R Y C O L L E C T I O N F R O M T E X T;
GEOMETRYCOLLECTIONFROMWKB:                      G E O M E T R Y C O L L E C T I O N F R O M W K B;
GEOMETRYFROMTEXT:                       G E O M E T R Y F R O M T E X T;
GEOMETRYFROMWKB:                        G E O M E T R Y F R O M W K B;
GEOMETRYN:                      G E O M E T R Y N;
GEOMETRYTYPE:                       G E O M E T R Y T Y P E;
GEOMFROMTEXT:                       G E O M F R O M T E X T;
GEOMFROMWKB:                        G E O M F R O M W K B;
GEOSTD8:                        G E O S T D '8';
GET_FORMAT:                     G E T '_' F O R M A T;
GET_LOCK:                       G E T '_' L O C K;
GLENGTH:                        G L E N G T H;
GLOBAL:                     G L O B A L;
GO:                     G O;
GOTO:                       G O T O;
GRANT:                      G R A N T;
GRANTS:                     G R A N T S;
GREATEST:                       G R E A T E S T;
GREEK:                      G R E E K;
GROUP:                      G R O U P;
GROUPING:                       G R O U P I N G;
GROUPING_ID:                        G R O U P I N G '_' I D;
GROUP_CONCAT:                       G R O U P '_' C O N C A T;
GTID_SUBSET:                        G T I D '_' S U B S E T;
GTID_SUBTRACT:                      G T I D '_' S U B T R A C T;
HADR:                       H A D R;
HANDLER:                        H A N D L E R;
HASH:                       H A S H;
HAVING:                     H A V I N G;
HEBREW:                     H E B R E W;
HELP:                       H E L P;
HEX:                        H E X;
HIDE:                       H I D E;
HIGH_PRIORITY:                      H I G H '_' P R I O R I T Y;
HONOR_BROKER_PRIORITY:                      H O N O R '_' B R O K E R '_' P R I O R I T Y;
HOST:                       H O S T;
HOSTS:                      H O S T S;
HOUR:                       H O U R;
HOURS:                      H O U R S;
HOUR_MICROSECOND:                       H O U R '_' M I C R O S E C O N D;
HOUR_MINUTE:                        H O U R '_' M I N U T E;
HOUR_SECOND:                        H O U R '_' S E C O N D;
HP8:                        H P '8';
IDENTIFIED:                     I D E N T I F I E D;
IDENTITY:                       I D E N T I T Y;
IDENTITYCOL:                        I D E N T I T Y C O L;
IDENTITY_INSERT:                        I D E N T I T Y '_' I N S E R T;
IF:                     I F;
IFNULL:                     I F N U L L;
IGNORE:                     I G N O R E;
IGNORE_NONCLUSTERED_COLUMNSTORE_INDEX:                      I G N O R E '_' N O N C L U S T E R E D '_' C O L U M N S T O R E '_' I N D E X;
IMMEDIATE:                      I M M E D I A T E;
IMPERSONATE:                        I M P E R S O N A T E;
IMPORT:                     I M P O R T;
IN:                     I N;
INCLUDE:                        I N C L U D E;
INCLUDING:                      I N C L U D I N G;
INCREMENT:                      I N C R E M E N T;
INCREMENTAL:                        I N C R E M E N T A L;
INDENT:                     I N D E N T;
INDEX:                      I N D E X;
INDEXED:                        I N D E X E D;
INDICATOR:                      I N D I C A T O R;
INDICES:                        I N D I C E S;
INET6_ATON:                     I N E T '6' '_' A T O N;
INET6_NTOA:                     I N E T '6' '_' N T O A;
INET_ATON:                      I N E T '_' A T O N;
INET_NTOA:                      I N E T '_' N T O A;
INFILE:                     I N F I L E;
INFINITE:                       I N F I N I T E;
INITIAL_SIZE:                       I N I T I A L '_' S I Z E;
INLINE:                     I N L I N E;
INNER:                      I N N E R;
INNODB:                     I N N O D B;
INOUT:                      I N O U T;
INPLACE:                        I N P L A C E;
INSENSITIVE:                        I N S E N S I T I V E;
INSERT:                     I N S E R T;
INSERTED:                       I N S E R T E D;
INSERT_METHOD:                      I N S E R T '_' M E T H O D;
INSTALL:                        I N S T A L L;
INSTANTIABLE:                       I N S T A N T I A B L E;
INSTEAD:                        I N S T E A D;
INSTR:                      I N S T R;
INT:                        I N T;
INTEGER:                        I N T E G E R;
INTERIORRINGN:                      I N T E R I O R R I N G N;
INTERNAL:                       I N T E R N A L;
INTERSECT:                      I N T E R S E C T;
INTERSECTS:                     I N T E R S E C T S;
INTERVAL:                       I N T E R V A L;
INTO:                       I N T O;
INVALIDATE:                     I N V A L I D A T E;
INVOKER:                        I N V O K E R;
IS:                     I S;
ISCLOSED:                       I S C L O S E D;
ISEMPTY:                        I S E M P T Y;
ISNULL:                     I S N U L L;
ISO:                        I S O;
ISOLATION:                      I S O L A T I O N;
ISSIMPLE:                       I S S I M P L E;
ISSUER:                     I S S U E R;
IS_FREE_LOCK:                       I S '_' F R E E '_' L O C K;
IS_IPV4:                        I S '_' I P V '4';
IS_IPV4_COMPAT:                     I S '_' I P V '4' '_' C O M P A T;
IS_IPV4_MAPPED:                     I S '_' I P V '4' '_' M A P P E D;
IS_IPV6:                        I S '_' I P V '6';
IS_USED_LOCK:                       I S '_' U S E D '_' L O C K;
ITERATE:                        I T E R A T E;
JAVA:                       J A V A;
JIS:                        J I S;
JOIN:                       J O I N;
JSON:                       J S O N;
KB:                     K B;
KEEP:                       K E E P;
KEEPFIXED:                      K E E P F I X E D;
KEY:                        K E Y;
KEYBCS2:                        K E Y B C S '2';
KEYS:                       K E Y S;
KEYSET:                     K E Y S E T;
KEY_BLOCK_SIZE:                     K E Y '_' B L O C K '_' S I Z E;
KILL:                       K I L L;
KOI8R:                      K O I '8' R;
KOI8U:                      K O I '8' U;
LAG:                        L A G;
LANGUAGE:                       L A N G U A G E;
LAST:                       L A S T;
LAST_INSERT_ID:                     L A S T '_' I N S E R T '_' I D;
LAST_VALUE:                     L A S T '_' V A L U E;
LATIN1:                     L A T I N '1';
LATIN2:                     L A T I N '2';
LATIN5:                     L A T I N '5';
LATIN7:                     L A T I N '7';
LCASE:                      L C A S E;
LEAD:                       L E A D;
LEADING:                        L E A D I N G;
LEAST:                      L E A S T;
LEAVE:                      L E A V E;
LEFT:                       L E F T;
LENGTH:                     L E N G T H;
LESS:                       L E S S;
LEVEL:                      L E V E L;
LIBRARY:                        L I B R A R Y;
LIKE:                       L I K E;
LIKE2:                      L I K E '2';
LIKE4:                      L I K E '4';
LIKEC:                      L I K E C;
LIMIT:                      L I M I T;
LINEAR:                     L I N E A R;
LINEFROMTEXT:                       L I N E F R O M T E X T;
LINEFROMWKB:                        L I N E F R O M W K B;
LINENO:                     L I N E N O;
LINES:                      L I N E S;
LINESTRING:                     L I N E S T R I N G;
LINESTRINGFROMTEXT:                     L I N E S T R I N G F R O M T E X T;
LINESTRINGFROMWKB:                      L I N E S T R I N G F R O M W K B;
LIST:                       L I S T;
LISTAGG:                        L I S T A G G;
LN:                     L N;
LOAD:                       L O A D;
LOAD_FILE:                      L O A D '_' F I L E;
LOCAL:                      L O C A L;
LOCALTIME:                      L O C A L T I M E;
LOCALTIMESTAMP:                     L O C A L T I M E S T A M P;
LOCATE:                     L O C A T E;
LOCK:                       L O C K;
LOCKED:                     L O C K E D;
LOCK_ESCALATION:                        L O C K '_' E S C A L A T I O N;
LOG:                        L O G;
LOG10:                      L O G '1' '0';
LOG2:                       L O G '2';
LOGFILE:                        L O G F I L E;
LOGIN:                      L O G I N;
LOGOFF:                     L O G O F F;
LOGON:                      L O G O N;
LOGS:                       L O G S;
LONG:                       L O N G;
LONGBLOB:                       L O N G B L O B;
LONGTEXT:                       L O N G T E X T;
LOOP:                       L O O P;
LOWER:                      L O W E R;
LOW_PRIORITY:                       L O W '_' P R I O R I T Y;
LPAD:                       L P A D;
LTRIM:                      L T R I M;
MACCE:                      M A C C E;
MACROMAN:                       M A C R O M A N;
MAIN:                       M A I N;
MAKEDATE:                       M A K E D A T E;
MAKETIME:                       M A K E T I M E;
MAKE_SET:                       M A K E '_' S E T;
MAP:                        M A P;
MARK:                       M A R K;
MASTER:                     M A S T E R;
MASTER_POS_WAIT:                        M A S T E R '_' P O S '_' W A I T;
MATCH:                      M A T C H;
MATCHED:                        M A T C H E D;
MAX:                        M A X;
MAXDOP:                     M A X D O P;
MAXRECURSION:                       M A X R E C U R S I O N;
MAXSIZE:                        M A X S I Z E;
MAXVALUE:                       M A X V A L U E;
MAX_CONNECTIONS_PER_HOUR:                       M A X '_' C O N N E C T I O N S '_' P E R '_' H O U R;
MAX_QUERIES_PER_HOUR:                       M A X '_' Q U E R I E S '_' P E R '_' H O U R;
MAX_ROWS:                       M A X '_' R O W S;
MAX_SIZE:                       M A X '_' S I Z E;
MAX_UPDATES_PER_HOUR:                       M A X '_' U P D A T E S '_' P E R '_' H O U R;
MAX_USER_CONNECTIONS:                       M A X '_' U S E R '_' C O N N E C T I O N S;
MB:                     M B;
MBRCONTAINS:                        M B R C O N T A I N S;
MBRDISJOINT:                        M B R D I S J O I N T;
MBREQUAL:                       M B R E Q U A L;
MBRINTERSECTS:                      M B R I N T E R S E C T S;
MBROVERLAPS:                        M B R O V E R L A P S;
MBRTOUCHES:                     M B R T O U C H E S;
MBRWITHIN:                      M B R W I T H I N;
MD5:                        M D '5';
MEASURES:                       M E A S U R E S;
MEDIAN:                     M E D I A N;
MEDIUM:                     M E D I U M;
MEDIUMBLOB:                     M E D I U M B L O B;
MEDIUMINT:                      M E D I U M I N T;
MEDIUMTEXT:                     M E D I U M T E X T;
MEMBER:                     M E M B E R;
MEMORY:                     M E M O R Y;
MEMORY_OPTIMIZED_DATA:                      M E M O R Y '_' O P T I M I Z E D '_' D A T A;
MERGE:                      M E R G E;
MICROSECOND:                        M I C R O S E C O N D;
MID:                        M I D;
MIN:                        M I N;
MINUS:                      M I N U S;
MINUTE:                     M I N U T E;
MINUTES:                        M I N U T E S;
MINUTE_MICROSECOND:                     M I N U T E '_' M I C R O S E C O N D;
MINUTE_SECOND:                      M I N U T E '_' S E C O N D;
MINVALUE:                       M I N V A L U E;
MIN_ACTIVE_ROWVERSION:                      M I N '_' A C T I V E '_' R O W V E R S I O N;
MIN_ROWS:                       M I N '_' R O W S;
MIXED_PAGE_ALLOCATION:                      M I X E D '_' P A G E '_' A L L O C A T I O N;
MLINEFROMTEXT:                      M L I N E F R O M T E X T;
MLINEFROMWKB:                       M L I N E F R O M W K B;
MLSLABEL:                       M L S L A B E L;
MODE:                       M O D E;
MODEL:                      M O D E L;
MODIFIES:                       M O D I F I E S;
MODIFY:                     M O D I F Y;
MONTH:                      M O N T H;
MONTHNAME:                      M O N T H N A M E;
MPOINTFROMTEXT:                     M P O I N T F R O M T E X T;
MPOINTFROMWKB:                      M P O I N T F R O M W K B;
MPOLYFROMTEXT:                      M P O L Y F R O M T E X T;
MPOLYFROMWKB:                       M P O L Y F R O M W K B;
MRG_MYISAM:                     M R G '_' M Y I S A M;
MULTILINESTRING:                        M U L T I L I N E S T R I N G;
MULTILINESTRINGFROMTEXT:                        M U L T I L I N E S T R I N G F R O M T E X T;
MULTILINESTRINGFROMWKB:                     M U L T I L I N E S T R I N G F R O M W K B;
MULTIPOINT:                     M U L T I P O I N T;
MULTIPOINTFROMTEXT:                     M U L T I P O I N T F R O M T E X T;
MULTIPOINTFROMWKB:                      M U L T I P O I N T F R O M W K B;
MULTIPOLYGON:                       M U L T I P O L Y G O N;
MULTIPOLYGONFROMTEXT:                       M U L T I P O L Y G O N F R O M T E X T;
MULTIPOLYGONFROMWKB:                        M U L T I P O L Y G O N F R O M W K B;
MULTISET:                       M U L T I S E T;
MULTI_USER:                     M U L T I '_' U S E R;
MUTEX:                      M U T E X;
MYISAM:                     M Y I S A M;
MYSQL:                      M Y S Q L;
NAME:                       N A M E;
NAMES:                      N A M E S;
NAME_CONST:                     N A M E '_' C O N S T;
KW_NAN:                        N A N;
NATIONAL:                       N A T I O N A L;
NATURAL:                        N A T U R A L;
NATURALN:                       N A T U R A L N;
NAV:                        N A V;
NCHAR:                      N C H A R;
NCHAR_CS:                       N C H A R '_' C S;
NCLOB:                      N C L O B;
NDB:                        N D B;
NDBCLUSTER:                     N D B C L U S T E R;
NESTED:                     N E S T E D;
NESTED_TRIGGERS:                        N E S T E D '_' T R I G G E R S;
NEW:                        N E W;
NEW_BROKER:                     N E W '_' B R O K E R;
NEXT:                       N E X T;
NO:                     N O;
NOAUDIT:                        N O A U D I T;
NOCACHE:                        N O C A C H E;
NOCHECK:                        N O C H E C K;
NOCOPY:                     N O C O P Y;
NOCOUNT:                        N O C O U N T;
NOCYCLE:                        N O C Y C L E;
NODEGROUP:                      N O D E G R O U P;
NOENTITYESCAPING:                       N O E N T I T Y E S C A P I N G;
NOEXPAND:                       N O E X P A N D;
NOMAXVALUE:                     N O M A X V A L U E;
NOMINVALUE:                     N O M I N V A L U E;
NONCLUSTERED:                       N O N C L U S T E R E D;
NONE:                       N O N E;
NON_TRANSACTED_ACCESS:                      N O N '_' T R A N S A C T E D '_' A C C E S S;
NOORDER:                        N O O R D E R;
NORECOMPUTE:                        N O R E C O M P U T E;
NOSCHEMACHECK:                      N O S C H E M A C H E C K;
NOT:                        N O T;
NOW:                        N O W;
NOWAIT:                     N O W A I T;
NO_WAIT:                        N O '_' W A I T;
NO_WRITE_TO_BINLOG:                     N O '_' W R I T E '_' T O '_' B I N L O G;
NTILE:                      N T I L E;
KW_NULL:                       N U L L;
NULLIF:                     N U L L I F;
NULLS:                      N U L L S;
NULL_LITERAL:                       N U L L '_' L I T E R A L;
NUMBER:                     N U M B E R;
NUMERIC:                        N U M E R I C;
NUMERIC_ROUNDABORT:                     N U M E R I C '_' R O U N D A B O R T;
NUMGEOMETRIES:                      N U M G E O M E T R I E S;
NUMINTERIORRINGS:                       N U M I N T E R I O R R I N G S;
NUMPOINTS:                      N U M P O I N T S;
NVARCHAR2:                      N V A R C H A R '2';
OBJECT:                     O B J E C T;
OCT:                        O C T;
OCTET_LENGTH:                       O C T E T '_' L E N G T H;
OF:                     O F;
OFF:                        O F F;
OFFLINE:                        O F F L I N E;
OFFSET:                     O F F S E T;
OFFSETS:                        O F F S E T S;
OID:                        O I D;
OJ:                     O J;
OLD:                        O L D;
OLD_PASSWORD:                       O L D '_' P A S S W O R D;
ON:                     O N;
ONLINE:                     O N L I N E;
ONLY:                       O N L Y;
OPEN:                       O P E N;
OPENDATASOURCE:                     O P E N D A T A S O U R C E;
OPENQUERY:                      O P E N Q U E R Y;
OPENROWSET:                     O P E N R O W S E T;
OPENXML:                        O P E N X M L;
OPTIMISTIC:                     O P T I M I S T I C;
OPTIMIZE:                       O P T I M I Z E;
OPTION:                     O P T I O N;
OPTIONALLY:                     O P T I O N A L L Y;
OPTIONS:                        O P T I O N S;
OR:                     O R;
ORADATA:                        O R A D A T A;
ORD:                        O R D;
ORDER:                      O R D E R;
ORDINALITY:                     O R D I N A L I T Y;
OSERROR:                        O S E R R O R;
OUT:                        O U T;
OUTER:                      O U T E R;
OUTFILE:                        O U T F I L E;
OUTPUT:                     O U T P U T;
OVER:                       O V E R;
OVERLAPS:                       O V E R L A P S;
OVERRIDING:                     O V E R R I D I N G;
OWNER:                      O W N E R;
PACKAGE:                        P A C K A G E;
PACK_KEYS:                      P A C K '_' K E Y S;
PAGE_VERIFY:                        P A G E '_' V E R I F Y;
PARALLEL_ENABLE:                        P A R A L L E L '_' E N A B L E;
PARAMETERIZATION:                       P A R A M E T E R I Z A T I O N;
PARAMETERS:                     P A R A M E T E R S;
PARENT:                     P A R E N T;
PARSER:                     P A R S E R;
PARTIAL:                        P A R T I A L;
PARTITION:                      P A R T I T I O N;
PARTITIONING:                       P A R T I T I O N I N G;
PARTITIONS:                     P A R T I T I O N S;
PASSING:                        P A S S I N G;
PASSWORD:                       P A S S W O R D;
PATH:                       P A T H;
PERCENT:                        P E R C E N T;
PERCENTILE_CONT:                        P E R C E N T I L E '_' C O N T;
PERCENTILE_DISC:                        P E R C E N T I L E '_' D I S C;
PERCENT_FOUND:                      P E R C E N T '_' F O U N D;
PERCENT_ISOPEN:                     P E R C E N T '_' I S O P E N;
PERCENT_NOTFOUND:                       P E R C E N T '_' N O T F O U N D;
PERCENT_RANK:                       P E R C E N T '_' R A N K;
PERCENT_ROWCOUNT:                       P E R C E N T '_' R O W C O U N T;
PERCENT_ROWTYPE:                        P E R C E N T '_' R O W T Y P E;
PERCENT_TYPE:                       P E R C E N T '_' T Y P E;
PERFOMANCE_SCHEMA:                      P E R F O M A N C E '_' S C H E M A;
PERIOD_ADD:                     P E R I O D '_' A D D;
PERIOD_DIFF:                        P E R I O D '_' D I F F;
PI:                     P I;
PIPELINED:                      P I P E L I N E D;
PIVOT:                      P I V O T;
PLAN:                       P L A N;
PLS_INTEGER:                        P L S '_' I N T E G E R;
PLUGIN:                     P L U G I N;
PLUGINS:                        P L U G I N S;
POINT:                      P O I N T;
POINTFROMTEXT:                      P O I N T F R O M T E X T;
POINTFROMWKB:                       P O I N T F R O M W K B;
POINTN:                     P O I N T N;
POLYFROMTEXT:                       P O L Y F R O M T E X T;
POLYFROMWKB:                        P O L Y F R O M W K B;
POLYGON:                        P O L Y G O N;
POLYGONFROMTEXT:                        P O L Y G O N F R O M T E X T;
POLYGONFROMWKB:                     P O L Y G O N F R O M W K B;
PORT:                       P O R T;
POSITION:                       P O S I T I O N;
POSITIVE:                       P O S I T I V E;
POSITIVEN:                      P O S I T I V E N;
POW:                        P O W;
POWER:                      P O W E R;
PRAGMA:                     P R A G M A;
PRECEDING:                      P R E C E D I N G;
PRECISION:                      P R E C I S I O N;
PREDICTION:                     P R E D I C T I O N;
PREDICTION_BOUNDS:                      P R E D I C T I O N '_' B O U N D S;
PREDICTION_COST:                        P R E D I C T I O N '_' C O S T;
PREDICTION_DETAILS:                     P R E D I C T I O N '_' D E T A I L S;
PREDICTION_PROBABILITY:                     P R E D I C T I O N '_' P R O B A B I L I T Y;
PREDICTION_SET:                     P R E D I C T I O N '_' S E T;
PRESENT:                        P R E S E N T;
PRESERVE:                       P R E S E R V E;
PRIMARY:                        P R I M A R Y;
PRINT:                      P R I N T;
PRIOR:                      P R I O R;
PRIVILEGES:                     P R I V I L E G E S;
PROC:                       P R O C;
PROCEDURE:                      P R O C E D U R E;
PROCESS:                        P R O C E S S;
PROCESSLIST:                        P R O C E S S L I S T;
PROFILE:                        P R O F I L E;
PROFILES:                       P R O F I L E S;
PROXY:                      P R O X Y;
PUBLIC:                     P U B L I C;
QUARTER:                        Q U A R T E R;
QUERY:                      Q U E R Y;
QUICK:                      Q U I C K;
QUOTE:                      Q U O T E;
QUOTED_IDENTIFIER:                      Q U O T E D '_' I D E N T I F I E R;
RADIANS:                        R A D I A N S;
RAISE:                      R A I S E;
RAISERROR:                      R A I S E R R O R;
RAND:                       R A N D;
RANDOM_BYTES:                       R A N D O M '_' B Y T E S;
RANGE:                      R A N G E;
RANK:                       R A N K;
RATIO_TO_REPORT:                        R A T I O '_' T O '_' R E P O R T;
RAW:                        R A W;
READ:                       R E A D;
READONLY:                       R E A D O N L Y;
READS:                      R E A D S;
READTEXT:                       R E A D T E X T;
READ_COMMITTED_SNAPSHOT:                        R E A D '_' C O M M I T T E D '_' S N A P S H O T;
READ_ONLY:                      R E A D '_' O N L Y;
READ_WRITE:                     R E A D '_' W R I T E;
REAL:                       R E A L;
REBUILD:                        R E B U I L D;
RECOMPILE:                      R E C O M P I L E;
RECONFIGURE:                        R E C O N F I G U R E;
RECORD:                     R E C O R D;
RECOVERY:                       R E C O V E R Y;
RECURSIVE_TRIGGERS:                     R E C U R S I V E '_' T R I G G E R S;
REDO_BUFFER_SIZE:                       R E D O '_' B U F F E R '_' S I Z E;
REDUNDANT:                      R E D U N D A N T;
REF:                        R E F;
REFERENCE:                      R E F E R E N C E;
REFERENCES:                     R E F E R E N C E S;
REFERENCING:                        R E F E R E N C I N G;
REGEXP:                     R E G E X P;
REGR_:                      R E G R '_';
REJECT:                     R E J E C T;
RELATIVE:                       R E L A T I V E;
RELAY:                      R E L A Y;
RELAYLOG:                       R E L A Y L O G;
RELEASE:                        R E L E A S E;
RELEASE_LOCK:                       R E L E A S E '_' L O C K;
RELIES_ON:                      R E L I E S '_' O N;
RELOAD:                     R E L O A D;
REMOTE:                     R E M O T E;
REMOVE:                     R E M O V E;
RENAME:                     R E N A M E;
REORGANIZE:                     R E O R G A N I Z E;
REPAIR:                     R E P A I R;
REPEAT:                     R E P E A T;
REPEATABLE:                     R E P E A T A B L E;
REPLACE:                        R E P L A C E;
REPLICATION:                        R E P L I C A T I O N;
REQUIRE:                        R E Q U I R E;
RESET:                      R E S E T;
RESPECT:                        R E S P E C T;
RESTORE:                        R E S T O R E;
RESTRICT:                       R E S T R I C T;
RESTRICTED_USER:                        R E S T R I C T E D '_' U S E R;
RESTRICT_REFERENCES:                        R E S T R I C T '_' R E F E R E N C E S;
RESULT:                     R E S U L T;
RESULT_CACHE:                       R E S U L T '_' C A C H E;
RETURN:                     R E T U R N;
RETURNING:                      R E T U R N I N G;
RETURNS:                        R E T U R N S;
REUSE:                      R E U S E;
REVERSE:                        R E V E R S E;
REVERT:                     R E V E R T;
REVOKE:                     R E V O K E;
RIGHT:                      R I G H T;
RLIKE:                      R L I K E;
ROBUST:                     R O B U S T;
ROLLBACK:                       R O L L B A C K;
ROLLUP:                     R O L L U P;
ROOT:                       R O O T;
ROUND:                      R O U N D;
ROUTINE:                        R O U T I N E;
ROW:                        R O W;
ROWCOUNT:                       R O W C O U N T;
ROWGUID:                        R O W G U I D;
ROWGUIDCOL:                     R O W G U I D C O L;
ROWID:                      R O W I D;
ROWS:                       R O W S;
ROW_COUNT:                      R O W '_' C O U N T;
ROW_FORMAT:                     R O W '_' F O R M A T;
ROW_NUMBER:                     R O W '_' N U M B E R;
RPAD:                       R P A D;
RTRIM:                      R T R I M;
RULE:                       R U L E;
RULES:                      R U L E S;
SAMPLE:                     S A M P L E;
SAVE:                       S A V E;
SAVEPOINT:                      S A V E P O I N T;
SCHEDULE:                       S C H E D U L E;
SCHEMA:                     S C H E M A;
SCHEMABINDING:                      S C H E M A B I N D I N G;
SCHEMACHECK:                        S C H E M A C H E C K;
SCN:                        S C N;
SCROLL:                     S C R O L L;
SCROLL_LOCKS:                       S C R O L L '_' L O C K S;
SEARCH:                     S E A R C H;
SECOND:                     S E C O N D;
SECONDS:                        S E C O N D S;
SECOND_MICROSECOND:                     S E C O N D '_' M I C R O S E C O N D;
SECURITY:                       S E C U R I T Y;
SECURITYAUDIT:                      S E C U R I T Y A U D I T;
SEC_TO_TIME:                        S E C '_' T O '_' T I M E;
SEED:                       S E E D;
SEGMENT:                        S E G M E N T;
SELECT:                     S E L E C T;
SELF:                       S E L F;
SEMANTICKEYPHRASETABLE:                     S E M A N T I C K E Y P H R A S E T A B L E;
SEMANTICSIMILARITYDETAILSTABLE:                     S E M A N T I C S I M I L A R I T Y D E T A I L S T A B L E;
SEMANTICSIMILARITYTABLE:                        S E M A N T I C S I M I L A R I T Y T A B L E;
SEPARATOR:                      S E P A R A T O R;
SEQUENCE:                       S E Q U E N C E;
SEQUENTIAL:                     S E Q U E N T I A L;
SERIALIZABLE:                       S E R I A L I Z A B L E;
SERIALLY_REUSABLE:                      S E R I A L L Y '_' R E U S A B L E;
SERVER:                     S E R V E R;
SERVERERROR:                        S E R V E R E R R O R;
SESSION:                        S E S S I O N;
SESSIONTIMEZONE:                        S E S S I O N T I M E Z O N E;
SESSION_USER:                       S E S S I O N '_' U S E R;
SET:                        S E T;
SETS:                       S E T S;
SETTINGS:                       S E T T I N G S;
SETUSER:                        S E T U S E R;
SHA:                        S H A;
SHA1:                       S H A '1';
SHA2:                       S H A '2';
SHARE:                      S H A R E;
SHARED:                     S H A R E D;
SHOW:                       S H O W;
SHOWPLAN:                       S H O W P L A N;
SHUTDOWN:                       S H U T D O W N;
SIBLINGS:                       S I B L I N G S;
SIGN:                       S I G N;
SIGNED:                     S I G N E D;
SIGNTYPE:                       S I G N T Y P E;
SIMPLE:                     S I M P L E;
SIMPLE_INTEGER:                     S I M P L E '_' I N T E G E R;
SIN:                        S I N;
SINGLE:                     S I N G L E;
SINGLE_USER:                        S I N G L E '_' U S E R;
SIZE:                       S I Z E;
SJIS:                       S J I S;
SKIP_:                      S K I P '_';
SLAVE:                      S L A V E;
SLEEP:                      S L E E P;
SLOW:                       S L O W;
SMALLINT:                       S M A L L I N T;
SNAPSHOT:                       S N A P S H O T;
SOCKET:                     S O C K E T;
SOME:                       S O M E;
SONAME:                     S O N A M E;
SOUNDEX:                        S O U N D E X;
SOUNDS:                     S O U N D S;
SPATIAL:                        S P A T I A L;
SPATIAL_WINDOW_MAX_CELLS:                       S P A T I A L '_' W I N D O W '_' M A X '_' C E L L S;
SPECIFICATION:                      S P E C I F I C A T I O N;
SQL:                        S Q L;
SQLDATA:                        S Q L D A T A;
SQLERROR:                       S Q L E R R O R;
SQLEXCEPTION:                       S Q L E X C E P T I O N;
SQLSTATE:                       S Q L S T A T E;
SQLWARNING:                     S Q L W A R N I N G;
SQL_BIG_RESULT:                     S Q L '_' B I G '_' R E S U L T;
SQL_BUFFER_RESULT:                      S Q L '_' B U F F E R '_' R E S U L T;
SQL_CACHE:                      S Q L '_' C A C H E;
SQL_CALC_FOUND_ROWS:                        S Q L '_' C A L C '_' F O U N D '_' R O W S;
SQL_NO_CACHE:                       S Q L '_' N O '_' C A C H E;
SQL_SMALL_RESULT:                       S Q L '_' S M A L L '_' R E S U L T;
SQL_THREAD_WAIT_AFTER_GTIDS:                        S Q L '_' T H R E A D '_' W A I T '_' A F T E R '_' G T I D S;
SQRT:                       S Q R T;
SRID:                       S R I D;
SSL:                        S S L;
STANDALONE:                     S T A N D A L O N E;
START:                      S T A R T;
STARTING:                       S T A R T I N G;
STARTPOINT:                     S T A R T P O I N T;
STARTS:                     S T A R T S;
STARTUP:                        S T A R T U P;
STATEMENT:                      S T A T E M E N T;
STATEMENT_ID:                       S T A T E M E N T '_' I D;
STATIC:                     S T A T I C;
STATISTICS:                     S T A T I S T I C S;
STATS_AUTO_RECALC:                      S T A T S '_' A U T O '_' R E C A L C;
STATS_PERSISTENT:                       S T A T S '_' P E R S I S T E N T;
STATS_SAMPLE_PAGES:                     S T A T S '_' S A M P L E '_' P A G E S;
STATS_STREAM:                       S T A T S '_' S T R E A M;
STATUS:                     S T A T U S;
STD:                        S T D;
STDDEV:                     S T D D E V;
STDDEV_POP:                     S T D D E V '_' P O P;
STDDEV_SAMP:                        S T D D E V '_' S A M P;
STDEV:                      S T D E V;
STDEVP:                     S T D E V P;
STORAGE:                        S T O R A G E;
STRAIGHT_JOIN:                      S T R A I G H T '_' J O I N;
STRCMP:                     S T R C M P;
STRING:                     S T R I N G;
STR_TO_DATE:                        S T R '_' T O '_' D A T E;
ST_AREA:                        S T '_' A R E A;
ST_ASBINARY:                        S T '_' A S B I N A R Y;
ST_ASTEXT:                      S T '_' A S T E X T;
ST_ASWKB:                       S T '_' A S W K B;
ST_ASWKT:                       S T '_' A S W K T;
ST_BUFFER:                      S T '_' B U F F E R;
ST_CENTROID:                        S T '_' C E N T R O I D;
ST_CONTAINS:                        S T '_' C O N T A I N S;
ST_CROSSES:                     S T '_' C R O S S E S;
ST_DIFFERENCE:                      S T '_' D I F F E R E N C E;
ST_DIMENSION:                       S T '_' D I M E N S I O N;
ST_DISJOINT:                        S T '_' D I S J O I N T;
ST_DISTANCE:                        S T '_' D I S T A N C E;
ST_ENDPOINT:                        S T '_' E N D P O I N T;
ST_ENVELOPE:                        S T '_' E N V E L O P E;
ST_EQUALS:                      S T '_' E Q U A L S;
ST_EXTERIORRING:                        S T '_' E X T E R I O R R I N G;
ST_GEOMCOLLFROMTEXT:                        S T '_' G E O M C O L L F R O M T E X T;
ST_GEOMCOLLFROMTXT:                     S T '_' G E O M C O L L F R O M T X T;
ST_GEOMCOLLFROMWKB:                     S T '_' G E O M C O L L F R O M W K B;
ST_GEOMETRYCOLLECTIONFROMTEXT:                      S T '_' G E O M E T R Y C O L L E C T I O N F R O M T E X T;
ST_GEOMETRYCOLLECTIONFROMWKB:                       S T '_' G E O M E T R Y C O L L E C T I O N F R O M W K B;
ST_GEOMETRYFROMTEXT:                        S T '_' G E O M E T R Y F R O M T E X T;
ST_GEOMETRYFROMWKB:                     S T '_' G E O M E T R Y F R O M W K B;
ST_GEOMETRYN:                       S T '_' G E O M E T R Y N;
ST_GEOMETRYTYPE:                        S T '_' G E O M E T R Y T Y P E;
ST_GEOMFROMTEXT:                        S T '_' G E O M F R O M T E X T;
ST_GEOMFROMWKB:                     S T '_' G E O M F R O M W K B;
ST_INTERIORRINGN:                       S T '_' I N T E R I O R R I N G N;
ST_INTERSECTION:                        S T '_' I N T E R S E C T I O N;
ST_INTERSECTS:                      S T '_' I N T E R S E C T S;
ST_ISCLOSED:                        S T '_' I S C L O S E D;
ST_ISEMPTY:                     S T '_' I S E M P T Y;
ST_ISSIMPLE:                        S T '_' I S S I M P L E;
ST_LINEFROMTEXT:                        S T '_' L I N E F R O M T E X T;
ST_LINEFROMWKB:                     S T '_' L I N E F R O M W K B;
ST_LINESTRINGFROMTEXT:                      S T '_' L I N E S T R I N G F R O M T E X T;
ST_LINESTRINGFROMWKB:                       S T '_' L I N E S T R I N G F R O M W K B;
ST_NUMGEOMETRIES:                       S T '_' N U M G E O M E T R I E S;
ST_NUMINTERIORRING:                     S T '_' N U M I N T E R I O R R I N G;
ST_NUMINTERIORRINGS:                        S T '_' N U M I N T E R I O R R I N G S;
ST_NUMPOINTS:                       S T '_' N U M P O I N T S;
ST_OVERLAPS:                        S T '_' O V E R L A P S;
ST_POINTFROMTEXT:                       S T '_' P O I N T F R O M T E X T;
ST_POINTFROMWKB:                        S T '_' P O I N T F R O M W K B;
ST_POINTN:                      S T '_' P O I N T N;
ST_POLYFROMTEXT:                        S T '_' P O L Y F R O M T E X T;
ST_POLYFROMWKB:                     S T '_' P O L Y F R O M W K B;
ST_POLYGONFROMTEXT:                     S T '_' P O L Y G O N F R O M T E X T;
ST_POLYGONFROMWKB:                      S T '_' P O L Y G O N F R O M W K B;
ST_SRID:                        S T '_' S R I D;
ST_STARTPOINT:                      S T '_' S T A R T P O I N T;
ST_SYMDIFFERENCE:                       S T '_' S Y M D I F F E R E N C E;
ST_TOUCHES:                     S T '_' T O U C H E S;
ST_UNION:                       S T '_' U N I O N;
ST_WITHIN:                      S T '_' W I T H I N;
ST_X:                       S T '_' X;
ST_Y:                       S T '_' Y;
SUBDATE:                        S U B D A T E;
SUBJECT:                        S U B J E C T;
SUBMULTISET:                        S U B M U L T I S E T;
SUBPARTITION:                       S U B P A R T I T I O N;
SUBPARTITIONS:                      S U B P A R T I T I O N S;
SUBSTITUTABLE:                      S U B S T I T U T A B L E;
SUBSTR:                     S U B S T R;
SUBSTRING:                      S U B S T R I N G;
SUBSTRING_INDEX:                        S U B S T R I N G '_' I N D E X;
SUBTIME:                        S U B T I M E;
SUBTYPE:                        S U B T Y P E;
SUCCESS:                        S U C C E S S;
SUM:                        S U M;
SUPER:                      S U P E R;
SUSPEND:                        S U S P E N D;
SWE7:                       S W E '7';
SYSDATE:                        S Y S D A T E;
SYSTEM_USER:                        S Y S T E M '_' U S E R;
TABLE:                      T A B L E;
TABLES:                     T A B L E S;
TABLESAMPLE:                        T A B L E S A M P L E;
TABLESPACE:                     T A B L E S P A C E;
TAKE:                       T A K E;
TAN:                        T A N;
TARGET_RECOVERY_TIME:                       T A R G E T '_' R E C O V E R Y '_' T I M E;
TB:                     T B;
TEMPORARY:                      T E M P O R A R Y;
TEMPTABLE:                      T E M P T A B L E;
TERMINATED:                     T E R M I N A T E D;
TEXT:                       T E X T;
TEXTIMAGE_ON:                       T E X T I M A G E '_' O N;
TEXTSIZE:                       T E X T S I Z E;
THAN:                       T H A N;
THE:                        T H E;
THEN:                       T H E N;
THROW:                      T H R O W;
TIES:                       T I E S;
TIME:                       T I M E;
TIMEDIFF:                       T I M E D I F F;
TIMESTAMP:                      T I M E S T A M P;
TIMESTAMPADD:                       T I M E S T A M P A D D;
TIMESTAMPDIFF:                      T I M E S T A M P D I F F;
TIMESTAMP_LTZ_UNCONSTRAINED:                        T I M E S T A M P '_' L T Z '_' U N C O N S T R A I N E D;
TIMESTAMP_TZ_UNCONSTRAINED:                     T I M E S T A M P '_' T Z '_' U N C O N S T R A I N E D;
TIMESTAMP_UNCONSTRAINED:                        T I M E S T A M P '_' U N C O N S T R A I N E D;
TIMEZONE_ABBR:                      T I M E Z O N E '_' A B B R;
TIMEZONE_HOUR:                      T I M E Z O N E '_' H O U R;
TIMEZONE_MINUTE:                        T I M E Z O N E '_' M I N U T E;
TIMEZONE_REGION:                        T I M E Z O N E '_' R E G I O N;
TIME_FORMAT:                        T I M E '_' F O R M A T;
TIME_TO_SEC:                        T I M E '_' T O '_' S E C;
TINYBLOB:                       T I N Y B L O B;
TINYINT:                        T I N Y I N T;
TINYTEXT:                       T I N Y T E X T;
TIS620:                     T I S '6' '2' '0';
TO:                     T O;
TOP:                        T O P;
TORN_PAGE_DETECTION:                        T O R N '_' P A G E '_' D E T E C T I O N;
TOUCHES:                        T O U C H E S;
TO_BASE64:                      T O '_' B A S E '6' '4';
TO_DAYS:                        T O '_' D A Y S;
TO_SECONDS:                     T O '_' S E C O N D S;
TRADITIONAL:                        T R A D I T I O N A L;
TRAILING:                       T R A I L I N G;
TRAN:                       T R A N;
TRANSACTION:                        T R A N S A C T I O N;
TRANSFORM_NOISE_WORDS:                      T R A N S F O R M '_' N O I S E '_' W O R D S;
TRANSLATE:                      T R A N S L A T E;
TREAT:                      T R E A T;
TRIGGER:                        T R I G G E R;
TRIGGERS:                       T R I G G E R S;
TRIM:                       T R I M;
TRUE:                       T R U E;
TRUNCATE:                       T R U N C A T E;
TRUSTWORTHY:                        T R U S T W O R T H Y;
TRY:                        T R Y;
TRY_CONVERT:                        T R Y '_' C O N V E R T;
TSEQUAL:                        T S E Q U A L;
TWO_DIGIT_YEAR_CUTOFF:                      T W O '_' D I G I T '_' Y E A R '_' C U T O F F;
TYPE:                       T Y P E;
TYPE_WARNING:                       T Y P E '_' W A R N I N G;
UCASE:                      U C A S E;
UCS2:                       U C S '2';
UJIS:                       U J I S;
UNBOUNDED:                      U N B O U N D E D;
UNCOMMITTED:                        U N C O M M I T T E D;
UNCOMPRESS:                     U N C O M P R E S S;
UNCOMPRESSED_LENGTH:                        U N C O M P R E S S E D '_' L E N G T H;
UNDEFINED:                      U N D E F I N E D;
UNDER:                      U N D E R;
UNDO:                       U N D O;
UNDOFILE:                       U N D O F I L E;
UNDO_BUFFER_SIZE:                       U N D O '_' B U F F E R '_' S I Z E;
UNHEX:                      U N H E X;
UNINSTALL:                      U N I N S T A L L;
UNION:                      U N I O N;
UNIQUE:                     U N I Q U E;
UNIX_TIMESTAMP:                     U N I X '_' T I M E S T A M P;
UNKNOWN:                        U N K N O W N;
UNLIMITED:                      U N L I M I T E D;
UNLOCK:                     U N L O C K;
UNPIVOT:                        U N P I V O T;
UNSIGNED:                       U N S I G N E D;
UNTIL:                      U N T I L;
UPDATE:                     U P D A T E;
UPDATED:                        U P D A T E D;
UPDATETEXT:                     U P D A T E T E X T;
UPDATEXML:                      U P D A T E X M L;
UPGRADE:                        U P G R A D E;
UPPER:                      U P P E R;
UPSERT:                     U P S E R T;
UROWID:                     U R O W I D;
USA:                        U S A;
USAGE:                      U S A G E;
USE:                        U S E;
USER:                       U S E R;
USER_RESOURCES:                     U S E R '_' R E S O U R C E S;
USE_FRM:                        U S E '_' F R M;
USING:                      U S I N G;
UTC_DATE:                       U T C '_' D A T E;
UTC_TIME:                       U T C '_' T I M E;
UTC_TIMESTAMP:                      U T C '_' T I M E S T A M P;
UTF16:                      U T F '1' '6';
UTF16LE:                        U T F '1' '6' L E;
UTF32:                      U T F '3' '2';
UTF8:                       U T F '8';
UTF8MB3:                        U T F '8' M B '3';
UTF8MB4:                        U T F '8' M B '4';
UUID:                       U U I D;
UUID_SHORT:                     U U I D '_' S H O R T;
VALIDATE:                       V A L I D A T E;
VALIDATE_PASSWORD_STRENGTH:                     V A L I D A T E '_' P A S S W O R D '_' S T R E N G T H;
VALUE:                      V A L U E;
VALUES:                     V A L U E S;
VAR:                        V A R;
VARBINARY:                      V A R B I N A R Y;
VARCHAR:                        V A R C H A R;
VARCHAR2:                       V A R C H A R '2';
VARIABLE:                       V A R I A B L E;
VARIABLES:                      V A R I A B L E S;
VARIANCE:                       V A R I A N C E;
VARP:                       V A R P;
VARRAY:                     V A R R A Y;
VARYING:                        V A R Y I N G;
VAR_:                       V A R '_';
VAR_POP:                        V A R '_' P O P;
VAR_SAMP:                       V A R '_' S A M P;
VERSION:                        V E R S I O N;
VERSIONS:                       V E R S I O N S;
VIEW:                       V I E W;
VIEWS:                      V I E W S;
VIEW_METADATA:                      V I E W '_' M E T A D A T A;
WAIT:                       W A I T;
WAITFOR:                        W A I T F O R;
WAIT_UNTIL_SQL_THREAD_AFTER_GTIDS:                      W A I T '_' U N T I L '_' S Q L '_' T H R E A D '_' A F T E R '_' G T I D S;
WARNING:                        W A R N I N G;
WARNINGS:                       W A R N I N G S;
WEEK:                       W E E K;
WEEKDAY:                        W E E K D A Y;
WEEKOFYEAR:                     W E E K O F Y E A R;
WEIGHT_STRING:                      W E I G H T '_' S T R I N G;
WELLFORMED:                     W E L L F O R M E D;
WHEN:                       W H E N;
WHENEVER:                       W H E N E V E R;
WHERE:                      W H E R E;
WHILE:                      W H I L E;
WITH:                       W I T H;
WITHIN:                     W I T H I N;
WORK:                       W O R K;
WRAPPER:                        W R A P P E R;
WRITE:                      W R I T E;
WRITETEXT:                      W R I T E T E X T;
X509:                       X '5' '0' '9';
XML:                        X M L;
XMLAGG:                     X M L A G G;
XMLATTRIBUTES:                      X M L A T T R I B U T E S;
XMLCAST:                        X M L C A S T;
XMLCOLATTVAL:                       X M L C O L A T T V A L;
XMLELEMENT:                     X M L E L E M E N T;
XMLEXISTS:                      X M L E X I S T S;
XMLFOREST:                      X M L F O R E S T;
XMLNAMESPACES:                      X M L N A M E S P A C E S;
XMLPARSE:                       X M L P A R S E;
XMLPI:                      X M L P I;
XMLQUERY:                       X M L Q U E R Y;
XMLROOT:                        X M L R O O T;
XMLSERIALIZE:                       X M L S E R I A L I Z E;
XMLTABLE:                       X M L T A B L E;
XOR:                        X O R;
YEAR:                       Y E A R;
YEARWEEK:                       Y E A R W E E K;
YEAR_MONTH:                     Y E A R '_' M O N T H;
YES:                        Y E S;
YMINTERVAL_UNCONSTRAINED:                       Y M I N T E R V A L '_' U N C O N S T R A I N E D;
ZEROFILL:                       Z E R O F I L L;
ZONE:                       Z O N E;


//-----------------
// Special keywords
//-----------------
DOLLAR_ACTION:                  '$' A C T I O N;
Y_FUNCTION:                     Y;
X_FUNCTION:                     X;
A_KEYWORD:                      A;



//----------
//----------
// Operators
//----------
//----------


//-----------------------
// Operators. Arithmetics
//-----------------------
STAR_SYMBOL:                    '*';
DIVIDE_SYMBOL:                  '/';
MODULE_SYMBOL:                  '%';
PLUS_SYMBOL:                    '+';
MINUS_SYMBOL:                   '-';

//-----------------------
// Operators. Comparation
//-----------------------
NULLSAVE_NOT_EQUAL_SYMBOL:      '<=>';
GREATER_EQUAL_SYMBOL:           '>=';
LESS_EQUAL_SYMBOL:              '<=';
NOT_EQUAL_SYMBOL1:              '<>';
NOT_EQUAL_SYMBOL2:              '!=';
EQUAL_SYMBOL:                   '=';
GREATER_SYMBOL:                 '>';
LESS_SYMBOL:                    '<';
EXCLAMATION_SYMBOL:             '!';

//---------------
// Operators. Bit
//---------------
BIT_NOT_OP_SYMBOL:              '~';
BIT_OR_OP_SYMBOL:               '|';
BIT_AND_OP_SYMBOL:              '&';
BIT_XOR_OP_SYMBOL:              '^';

//----------------
// Alt. logical op
//----------------
RSHIFT_OP_SYMBOL:               '<<';
LSHIFT_OP_SYMBOL:               '>>';
LOGICAL_OR_OP_SYMBOL:           '||';
LOCICAL_AND_OP_SYMBOL:          '&&';

//---------------------
// Constructors symbols
//---------------------
DOT_SYMBOL:                     '.';
LR_BRACKET_SYMBOL:              '(';
RR_BRACKET_SYMBOL:              ')';
COMMA_SYMBOL:                   ',';
SEMI_SYMBOL:                    ';';
AT_SIGN_SYMBOL:                 '@';
SINGLE_QUOTE_SYMBOL:            '\'';
DOUBLE_QUOTE_SYMBOL:            '"';
REVERSE_QUOTE_SYMBOL:           '`';
COLON_SYMBOL:                   ':';
SHARP_SYMBOL:                   '#';
DOLLAR_SYMBOL:                  '$'; // TSQL
UNDERLINE_SYMBOL:               '_'; // TSQL
DOUBLE_PERIOD_SYMBOL:           '.' '.'; // Oracle
DOUBLE_ASTERISK_SYMBOL:         '**'; // Oracle

// ------------
// Spec.symbols
// ------------
DIV:                            D I V;
MOD:                            M O D;

//-------------------
// Operators. Assigns
//-------------------
VAR_ASSIGN_SYMBOL:              ':=';
PLUS_ASSIGN_SYMBOL:             '+=';
MINUS_ASSIGN_SYMBOL:            '-=';
MULT_ASSIGN_SYMBOL:             '*=';
DIV_ASSIGN_SYMBOL:              '/=';
MOD_ASSIGN_SYMBOL:              '%=';
AND_ASSIGN_SYMBOL:              '&=';
XOR_ASSIGN_SYMBOL:              '^=';
OR_ASSIGN_SYMBOL:               '|=';




//---------
//---------
// Charsets
//---------
//---------
CHARSET_REVERSE_QOUTE_STRING:   '`' CHARSET_NAME '`';


//-------------
//-------------
// File's sizes
//-------------
//-------------
FILESIZE_LITERAL:               DEC_DIGIT+ (K|M|G|T);


//-------------------
//-------------------
// Literal Primitives
//-------------------
//-------------------
START_NATIONAL_STRING_LITERAL:  N SQUOTA_STRING;
STRING_LITERAL:                 DQUOTA_STRING | SQUOTA_STRING;
DECIMAL_LITERAL:                DEC_DIGIT+;
HEXADECIMAL_LITERAL:            X '\'' (HEX_DIGIT HEX_DIGIT)+ '\'' 
                                | '0' X HEX_DIGIT+;

REAL_LITERAL:                   (
                                DEC_DIGIT+ '.' (DEC_DIGIT+ | EXPONENT_NUM_PART) 
                                | (DEC_DIGIT+ '.' | EXPONENT_NUM_PART)
                                | '.' (DEC_DIGIT+ | EXPONENT_NUM_PART)
                                ) (D|F)?; // MySQL + Oracle
NULL_SPEC_LITERAL:              NULL_SPEC_LITERAL_L;
BIT_STRING:                     BIT_STRING_L;
STRING_CHARSET_NAME:            '_' CHARSET_NAME;
CHAR_STRING_PERL:               Q ( QS_ANGLE | QS_BRACE | QS_BRACK | QS_PAREN) -> type(STRING_LITERAL); // Oracle


//-------------------
//-------------------
// Hack for dotID
// Prevent recognize string: .123somelatin AS ((.123), FLOAT_LITERAL), ((somelatin), ID)
//  it must recoginze: .123somelatin AS ((.), DOT), (123somelatin, ID)
//-------------------
//-------------------
DOT_ID:                         '.' ID_LITERAL;

// Labels (or BindVar in Oracle)
LABEL_MARK:                     ':' (ID_LITERAL | DQUOTA_STRING); // MySQL + Oracle



//-------------------------------------------------
//-------------------------------------------------
// Identifiers - must define LAST. Before Fragments
//-------------------------------------------------
//-------------------------------------------------
// TODO: Identifiers cat be not only Latin
// TODO: What to do with single quote identifiers????
ID:                             ID_LITERAL;
DOUBLE_QUOTE_ID:                '"' ~'"'+ '"'; // TSQL and Oracle
SQUARE_BRACKET_ID:              '[' ~']'+ ']'; // TSQL
REVERSE_QUOTE_ID:               '`' ~'`'+ '`';
STRING_USER_NAME:               (SQUOTA_STRING | DQUOTA_STRING | BQUOTA_STRING | ID_LITERAL) '@' (SQUOTA_STRING | DQUOTA_STRING | BQUOTA_STRING | ID_LITERAL);
LOCAL_ID:                       '@'
                                (
                                    [a-zA-Z0-9._$@#]+  //TSQL +MySQL
                                    | SQUOTA_STRING
                                    | DQUOTA_STRING
                                    | BQUOTA_STRING
                                );

// TODO: is it necessary describe all sys variables ?
GLOBAL_ID:                      '@' '@' 
                                (
                                    [a-zA-Z0-9._$]+ 
                                    | BQUOTA_STRING
                                );

// --------------------------------
// Fragments for Literal primitives
// --------------------------------
fragment CHARSET_NAME:          ARMSCII8 | ASCII | BIG5 | BINARY | CP1250 | CP1251 | CP1256 | CP1257 | CP850 
                                | CP852 | CP866 | CP932 | DEC8 | EUCJPMS | EUCKR | GB2312 | GBK | GEOSTD8 
                                | GREEK | HEBREW | HP8 | KEYBCS2 | KOI8R | KOI8U | LATIN1 | LATIN2 | LATIN5 
                                | LATIN7 | MACCE | MACROMAN | SJIS | SWE7 | TIS620 | UCS2 | UJIS | UTF16 
                                | UTF16LE | UTF32 | UTF8 | UTF8MB4;

fragment EXPONENT_NUM_PART:     DEC_DIGIT+ E '-'? DEC_DIGIT+;
fragment ID_LITERAL:            [a-zA-Z_$0-9]*?[a-zA-Z_$]+?[a-zA-Z_$0-9]*;
fragment DQUOTA_STRING:         '"' ( '\\'. | '""' | ~('"'| '\\') )* '"';
fragment SQUOTA_STRING:         '\'' ('\\'. | '\'\'' | ~('\'' | '\\'))* '\'';
fragment BQUOTA_STRING:         '`' ( '\\'. | '``' | ~('`'|'\\'))* '`';
fragment HEX_DIGIT:             [0-9A-Fa-f];
fragment DEC_DIGIT:             [0-9];
fragment NULL_SPEC_LITERAL_L:   '\\' 'N';
fragment BIT_STRING_L:          [bB] '\'' [01]+ '\''; // MySQL+Oracle

// Oracle fragments
fragment QS_ANGLE:              '\'' '<' .*? '>' '\'' ;
fragment QS_BRACE:              '\'' '{' .*? '}' '\'' ;
fragment QS_BRACK:              '\'' '[' .*? ']' '\'' ;
fragment QS_PAREN:              '\'' '(' .*? ')' '\'' ;
fragment QS_OTHER_CH:           ~('<' | '{' | '[' | '(' | ' ' | '\t' | '\n' | '\r');


// -------
// Letters
// -------
fragment A: [aA];
fragment B: [bB];
fragment C: [cC];
fragment D: [dD];
fragment E: [eE];
fragment F: [fF];
fragment G: [gG];
fragment H: [hH];
fragment I: [iI];
fragment J: [jJ];
fragment K: [kK];
fragment L: [lL];
fragment M: [mM];
fragment N: [nN];
fragment O: [oO];
fragment P: [pP];
fragment Q: [qQ];
fragment R: [rR];
fragment S: [sS];
fragment T: [tT];
fragment U: [uU];
fragment V: [vV];
fragment W: [wW];
fragment X: [xX];
fragment Y: [yY];
fragment Z: [zZ];


// -------
// End
// -------
ERROR:      .;