package ch.hsr.lecture09.typeerasure;

import ch.hsr.lecture09.stack.Stack;

public class StackTest {

    public static void main(String[] argv) {
        var stack = new Stack<String>();
        stack.push("A");
        String value = stack.pop();
    }

}

/*
 * Type Erasure:
 *
 * Disassemble Class File:
 * > javap -c ch/hsr/lecture09/StackTest.class

Compiled from "StackTest.java"
public class ch.hsr.lecture09.StackTest {
  public ch.hsr.lecture09.StackTest();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: new           #2                  // class ch/hsr/lecture09/stack/Stack
       3: dup
       4: invokespecial #3                  // Method ch/hsr/lecture09/stack/Stack."<init>":()V
       7: astore_1
       8: aload_1
       9: ldc           #4                  // String A
      11: invokevirtual #5                  // Method ch/hsr/lecture09/stack/Stack.push:(Ljava/lang/Object;)V   <-- Object
      14: aload_1
      15: invokevirtual #6                  // Method ch/hsr/lecture09/stack/Stack.pop:()Ljava/lang/Object;     <-- Object
      18: checkcast     #7                  // class java/lang/String                                           <-- Dynamic Class Cast: (String)stack.pop();
      21: astore_2
      22: return
}

 * Compiler: checks generic types AND deletes them.
 * Runtime: does not have information about generics (apart from reflection).
 *
 * Reason:
 * - backward compatible : old (without generics) code runs on new VMs
 * - forward compatible  : new code (with generics) runs on old VMs
 *
 * forward:          backward:
 * VM_old   -------- VM_new
 * code_new -------- code_old
 */