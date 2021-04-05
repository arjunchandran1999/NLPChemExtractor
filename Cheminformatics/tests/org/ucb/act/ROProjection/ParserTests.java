/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ucb.act.ROProjection;

import chemaxon.standardizer.Standardizer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import org.ucb.act.utils.ChemAxonUtils;

/**
 *
 * @author jesusdelrio
 */

public class ParserTests {
    
    @Test
    //SMILES and inchis from 21 chemicals obtained from PubChem. The method "smileInchiConverter" is tested
    //to corroborate if SMILES are succesfully converted into the right inchi.
    public void testSmileInchiConverter() throws Exception {
        
        ChemAxonUtils.license();
        Parser parser = new Parser();
        Set<String> succesfulConvertion= new HashSet();
        
        HashMap<String,String> smileInchiSet= new HashMap();
        
        //smiles and inchi form 21 random chemicals 
        smileInchiSet.put("CC1=C(C(CCC1)(C)C)/C=C/C(=C/C=C/C(=C/CO)/C)/C", "InChI=1S/C20H30O/c1-16(8-6-9-17(2)13-15-21)11-12-19-18(3)10-7-14-20(19,4)5/h6,8-9,11-13,21H,7,10,14-15H2,1-5H3/b9-6+,12-11+,16-8+,17-13+");
        smileInchiSet.put("C(C[C@@H](C(=O)O)N)CN=C(N)N","InChI=1S/C6H14N4O2/c7-4(5(11)12)2-1-3-10-6(8)9/h4H,1-3,7H2,(H,11,12)(H4,8,9,10)/t4-/m0/s1");
        smileInchiSet.put("C(CCN)C[C@@H](C(=O)O)N","InChI=1S/C6H14N2O2/c7-4-2-1-3-5(8)6(9)10/h5H,1-4,7-8H2,(H,9,10)/t5-/m0/s1");
        smileInchiSet.put("C1=CC=C2C(=C1)C(=CN2)C[C@@H](C(=O)O)N","InChI=1S/C11H12N2O2/c12-9(11(14)15)5-7-6-13-10-4-2-1-3-8(7)10/h1-4,6,9,13H,5,12H2,(H,14,15)/t9-/m0/s1");
        smileInchiSet.put("CC(=O)OC1=CC=CC=C1C(=O)O","InChI=1S/C9H8O4/c1-6(10)13-8-5-3-2-4-7(8)9(11)12/h2-5H,1H3,(H,11,12)");
        smileInchiSet.put("C1C=CN(C=C1C(=O)N)[C@H]2[C@@H]([C@@H]([C@H](O2)COP(=O)(O)OP(=O)(O)OC[C@@H]3[C@H]([C@H]([C@@H](O3)N4C=NC5=C(N=CN=C54)N)O)O)O)O","InChI=1S/C21H29N7O14P2/c22-17-12-19(25-7-24-17)28(8-26-12)21-16(32)14(30)11(41-21)6-39-44(36,37)42-43(34,35)38-5-10-13(29)15(31)20(40-10)27-3-1-2-9(4-27)18(23)33/h1,3-4,7-8,10-11,13-16,20-21,29-32H,2,5-6H2,(H2,23,33)(H,34,35)(H,36,37)(H2,22,24,25)/t10-,11-,13-,14-,15-,16-,20-,21-/m1/s1");
        smileInchiSet.put("C(CN(CC(=O)O)CC(=O)O)N(CC(=O)O)CC(=O)O","InChI=1S/C10H16N2O8/c13-7(14)3-11(4-8(15)16)1-2-12(5-9(17)18)6-10(19)20/h1-6H2,(H,13,14)(H,15,16)(H,17,18)(H,19,20)");
        smileInchiSet.put("CSCCC(C(=O)O)N","InChI=1S/C5H11NO2S/c1-9-3-2-4(6)5(7)8/h4H,2-3,6H2,1H3,(H,7,8)");
        smileInchiSet.put("C([C@@H]([C@@H]1C(=C(C(=O)O1)O)O)O)O","InChI=1S/C6H8O6/c7-1-2(8)5-3(9)4(10)6(11)12-5/h2,5,7-10H,1H2/t2-,5+/m0/s1");
        smileInchiSet.put("C1[C@H]([C@@H]([C@H]([C@@H]([C@H]1N)O[C@@H]2[C@@H]([C@H]([C@@H]([C@H](O2)CN)O)O)O)O)O[C@@H]3[C@@H]([C@H]([C@@H]([C@H](O3)CO)O)N)O)N","InChI=1S/C18H36N4O11/c19-2-6-10(25)12(27)13(28)18(30-6)33-16-5(21)1-4(20)15(14(16)29)32-17-11(26)8(22)9(24)7(3-23)31-17/h4-18,23-29H,1-3,19-22H2/t4-,5+,6-,7-,8+,9-,10-,11-,12+,13-,14-,15+,16-,17-,18-/m1/s1");
        smileInchiSet.put("CC(C1CCC(C(O1)OC2C(CC(C(C2O)OC3C(C(C(CO3)(C)O)NC)O)N)N)N)NC","InChI=1S/C21H43N5O7/c1-9(25-3)13-6-5-10(22)19(31-13)32-16-11(23)7-12(24)17(14(16)27)33-20-15(28)18(26-4)21(2,29)8-30-20/h9-20,25-29H,5-8,22-24H2,1-4H3");
        smileInchiSet.put("C1[C@H]([C@@H]([C@H]([C@@H]([C@H]1N)O[C@@H]2[C@@H]([C@H]([C@@H]([C@H](O2)CN)O)O)N)O[C@H]3[C@@H]([C@@H]([C@H](O3)CO)O[C@@H]4[C@@H]([C@H]([C@@H]([C@@H](O4)CN)O)O)N)O)O)N","InChI=1S/C23H46N6O13/c24-2-7-13(32)15(34)10(28)21(37-7)40-18-6(27)1-5(26)12(31)20(18)42-23-17(36)19(9(4-30)39-23)41-22-11(29)16(35)14(33)8(3-25)38-22/h5-23,30-36H,1-4,24-29H2/t5-,6+,7-,8+,9-,10-,11-,12+,13-,14-,15-,16-,17-,18-,19-,20-,21-,22-,23+/m1/s1");
        smileInchiSet.put("CNC[C@@H](C1=CC(=C(C=C1)O)O)O","InChI=1S/C9H13NO3/c1-10-5-9(13)6-2-3-7(11)8(12)4-6/h2-4,9-13H,5H2,1H3/t9-/m0/s1");
        smileInchiSet.put("C1=CC(=C(C=C1CCN)O)O","InChI=1S/C8H11NO2/c9-4-3-6-1-2-7(10)8(11)5-6/h1-2,5,10-11H,3-4,9H2");
        smileInchiSet.put("C1=CC2=C(C=C1O)C(=CN2)CCN","InChI=1S/C10H12N2O/c11-4-3-7-6-12-10-2-1-8(13)5-9(7)10/h1-2,5-6,12-13H,3-4,11H2");
        smileInchiSet.put("CCN(CC)C(=O)[C@H]1CN([C@@H]2CC3=CNC4=CC=CC(=C34)C2=C1)C","InChI=1S/C20H25N3O/c1-4-23(5-2)20(24)14-9-16-15-7-6-8-17-19(15)13(11-21-17)10-18(16)22(3)12-14/h6-9,11,14,18,21H,4-5,10,12H2,1-3H3/t14-,18-/m1/s1");
        smileInchiSet.put("CC(C)N(C(C)C)P(OCCC#N)O[C@@H]1[C@H](O[C@H]([C@@H]1O[Si](C)(C)C(C)(C)C)N2C=NC3=C2N=C(NC3=O)NC(=O)COC4=CC=C(C=C4)C(C)(C)C)COC(C5=CC=CC=C5)(C6=CC=C(C=C6)OC)C7=CC=C(C=C7)OC","InChI=1S/C58H76N7O10PSi/c1-38(2)65(39(3)4)76(72-34-18-33-59)74-50-47(35-71-58(41-19-16-15-17-20-41,42-23-27-44(68-11)28-24-42)43-25-29-45(69-12)30-26-43)73-54(51(50)75-77(13,14)57(8,9)10)64-37-60-49-52(64)62-55(63-53(49)67)61-48(66)36-70-46-31-21-40(22-32-46)56(5,6)7/h15-17,19-32,37-39,47,50-51,54H,18,34-36H2,1-14H3,(H2,61,62,63,66,67)/t47-,50-,51-,54-,76?/m1/s1");
        smileInchiSet.put("CCCCCC1=CC(=C2[C@@H]3C=C(CC[C@H]3C(OC2=C1)(C)C)C)O","InChI=1S/C21H30O2/c1-5-6-7-8-15-12-18(22)20-16-11-14(2)9-10-17(16)21(3,4)23-19(20)13-15/h11-13,16-17,22H,5-10H2,1-4H3/t16-,17-/m1/s1");
        smileInchiSet.put("CCCCCC1=CC(=C(C(=C1)O)[C@@H]2C=C(CC[C@H]2C(=C)C)C)O","InChI=1S/C21H30O2/c1-5-6-7-8-16-12-19(22)21(20(23)13-16)18-11-15(4)9-10-17(18)14(2)3/h11-13,17-18,22-23H,2,5-10H2,1,3-4H3/t17-,18+/m0/s1");
        smileInchiSet.put("C[C@]12CC[C@H]3[C@H]([C@@H]1CC[C@@H]2O)CCC4=CC(=O)CC[C@]34C","InChI=1S/C19H28O2/c1-18-9-7-13(20)11-12(18)3-4-14-15-5-6-17(21)19(15,2)10-8-16(14)18/h11,14-17,21H,3-10H2,1-2H3/t14-,15-,16-,17-,18-,19-/m0/s1");
        smileInchiSet.put("CN(C)CCC1=CNC2=C1C(=CC=C2)OP(=O)(O)O","InChI=1S/C12H17N2O4P/c1-14(2)7-6-9-8-13-10-4-3-5-11(12(9)10)18-19(15,16)17/h3-5,8,13H,6-7H2,1-2H3,(H2,15,16,17)");
        
        for (Map.Entry set: smileInchiSet.entrySet()){
            String inchi = parser.smileInchiConverter(set.getKey().toString());
         
            if (inchi.equals(set.getValue().toString())){
                succesfulConvertion.add(inchi);  
            }            
        }
        assertTrue(succesfulConvertion.size()==21);
    } 
    
    @Test
    //Test to see if data from csv is succesfully parsed into a HashMap
    public void parsingTest() throws Exception {
        
        ChemAxonUtils.license();
        Parser parser = new Parser();
        
        HashMap<String,HashMap<String,String>>  revisedListOfChemicals= new HashMap();
        HashMap<String,String> revisedNameInchi = new HashMap();
        HashMap<String,String> revisedNameInchi1 = new HashMap();
        HashMap<String,String> revisedNameInchi2 = new HashMap();
        
        //First row
        revisedNameInchi.put("manganese(ii)", "InChI=1S/Mn/q+2");
        revisedNameInchi.put("glucosamine", "InChI=1S/C6H13NO5/c7-3-5(10)4(9)2(1-8)12-6(3)11/h2-6,8-11H,1,7H2/t2-,3-,4-,5-,6?/m1/s1");
        revisedNameInchi.put("mannose", "InChI=1S/C6H12O6/c7-1-3(9)5(11)6(12)4(10)2-8/h1,3-6,8-12H,2H2/t3-,4-,5-,6-/m1/s1");
        revisedListOfChemicals.put("10.1006/abbi.1993.1008,13", revisedNameInchi);
        
        //Second row
        revisedNameInchi1.put("sugar", "InChI=1S/C12H22O11/c13-1-4-6(16)8(18)9(19)11(21-4)23-12(3-15)10(20)7(17)5(2-14)22-12/h4-11,13-20H,1-3H2/t4-,5-,6-,7-,8+,9-,10+,11-,12+/m1/s1");
        revisedNameInchi1.put("mannose", "InChI=1S/C6H12O6/c7-1-3(9)5(11)6(12)4(10)2-8/h1,3-6,8-12H,2H2/t3-,4-,5-,6-/m1/s1");
        revisedNameInchi1.put("h", "InChI=1S/H");
        revisedListOfChemicals.put("10.1006/abbi.1993.1077,10", revisedNameInchi1);
        
        //Third row
        revisedNameInchi2.put("cellobiose", "InChI=1S/C12H22O11/c13-1-3-5(15)6(16)9(19)12(22-3)23-10-4(2-14)21-11(20)8(18)7(10)17/h3-20H,1-2H2");
        revisedNameInchi2.put("cellobionolactone", "InChI=1S/C12H20O11/c13-1-3-5(15)6(16)9(19)12(22-3)23-10-4(2-14)21-11(20)8(18)7(10)17/h3-10,12-19H,1-2H2/t3-,4-,5-,6+,7-,8-,9-,10-,12+/m1/s1");
        revisedListOfChemicals.put("10.1006/abbi.1993.1098,4", revisedNameInchi2);
        
        // Key->doi and sentence #, Value->HashMap(key->name, value->Inchis)
        HashMap<String,HashMap<String,String>> listOfChemicals = parser.csvRun("./parserTestData.csv");
        
        assertTrue(revisedListOfChemicals.equals(listOfChemicals));
        
    }
}
