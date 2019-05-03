package executargramatica;

public interface Constants extends ScannerConstants, ParserConstants
{
    int EPSILON  = 0;
    int DOLLAR   = 1;

    int t_ID = 2;
    int t_CONST_INT = 3;
    int t_CONST_DOUBLE = 4;
    int t_CONST_CHAR = 5;
    int t_CONST_STR = 6;
    int t_CONST_HEX = 7;
    int t_CONST_BIN = 8;
    int t_BB_RS = 9;
    int t_BB_LS = 10;
    int t_BB_E = 11;
    int t_BB_OU = 12;
    int t_BB_NOT = 13;
    int t_BB_XOR = 14;
    int t_OP_SOMA = 15;
    int t_OP_SUBT = 16;
    int t_OP_MULT = 17;
    int t_OP_DIV = 18;
    int t_OP_MOD = 19;
    int t_OP_INCR = 20;
    int t_OP_DECR = 21;
    int t_RL_GT = 22;
    int t_RL_LT = 23;
    int t_RL_GE = 24;
    int t_RL_LE = 25;
    int t_RL_EQ = 26;
    int t_RL_NE = 27;
    int t_LQ_ND = 28;
    int t_LQ_OR = 29;
    int t_LQ_NT = 30;
    int t_SLINE_CM = 31;
    int t_MLINE_CM = 32;
    int t_ABRE_PARENTESES = 33;
    int t_FECHA_PARENTESES = 34;
    int t_ABRE_COLCHETE = 35;
    int t_FECHA_COLCHETE = 36;
    int t_ABRE_CHAVE = 37;
    int t_FECHA_CHAVE = 38;
    int t_IF = 39;
    int t_ELSE = 40;
    int t_ELSE_IF = 41;
    int t_DO = 42;
    int t_WHILE = 43;
    int t_FOR = 44;
    int t_PROGRAM = 45;
    int t_DOUBLE = 46;
    int t_INT = 47;
    int t_STRING = 48;
    int t_CHAR = 49;
    int t_FUNCTION = 50;
    int t_BEGIN = 51;
    int t_END = 52;
    int t_CONST = 53;
    int t_LET = 54;
    int t_MAIN = 55;
    int t_READ = 56;
    int t_WRITE = 57;
    int t_DEL_PT = 58;
    int t_DEL_VG = 59;
    int t_DEL_PV = 60;
    int t_DEL_DP = 61;
    int t_ATRIBUICAO = 62;

}
