package edu.hw11.task3;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import org.jetbrains.annotations.NotNull;

public class FibonacciByteCodeAppender implements ByteCodeAppender {

    @Override
    public @NotNull Size apply(
        MethodVisitor mv,
        Implementation.@NotNull Context context,
        MethodDescription instrumentedMethod
    ) {
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ILOAD, 1);

        Label ifConditionLabel = new Label();
        mv.visitJumpInsn(Opcodes.IFLE, ifConditionLabel);

        mv.visitInsn(Opcodes.LCONST_1);
        mv.visitInsn(Opcodes.LCONST_1);

        Label loopStartLabel = new Label();
        mv.visitLabel(loopStartLabel);
        mv.visitInsn(Opcodes.LADD);
        mv.visitVarInsn(Opcodes.IINC, 1);
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitJumpInsn(Opcodes.IFLE, loopStartLabel);

        mv.visitLabel(ifConditionLabel);
        mv.visitInsn(Opcodes.LRETURN);

        mv.visitMaxs(2, 2);
        mv.visitEnd();

        return new Size(2, instrumentedMethod.getStackSize());
    }
}
