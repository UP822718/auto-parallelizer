    package parallelizer.model;

import gen.CPPParser;
import parallelizer.Translator;

import java.util.LinkedList;

/**
 * Created by milderhc on 16/05/17.
 */
public class Function implements Comparable<Function> {

    private LinkedList<Block> flowGraph;
    private String id;
    private CPPParser.FunctionBodyContext ctx;

    public Function(String id, CPPParser.FunctionBodyContext ctx) {
        this.id = id;
        this.ctx = ctx;
        this.flowGraph = new LinkedList<>();
    }

    public Function(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public CPPParser.FunctionBodyContext getCtx() {
        return ctx;
    }


    public void buildFlowGraph () {
        Block currentBlock = new Block();
        for (CPPParser.InstructionContext inst : ctx.instruction()) {
            boolean empty = currentBlock.getInstructions().isEmpty();
            if (inst.forBlock() != null) {
                flowGraph.add(currentBlock);
                Block next = new Block();
                if( empty ) currentBlock.addInstruction( inst );
                else next.addInstruction( inst );
                currentBlock = next;
            } else if (inst.whileBlock() != null) {
                flowGraph.add(currentBlock);
                Block next = new Block();
                if( empty ) currentBlock.addInstruction( inst );
                else next.addInstruction( inst );
                currentBlock = next;
            } else if (inst.doWhileBlock() != null) {
                flowGraph.add(currentBlock);
                Block next = new Block();
                if( empty ) currentBlock.addInstruction( inst );
                else next.addInstruction( inst );
                currentBlock = next;
            } else if (inst.scope() != null) {
                flowGraph.add(currentBlock);
                Block next = new Block();
                if( empty ) currentBlock.addInstruction( inst );
                else next.addInstruction( inst );
                currentBlock = next;
            } else if (inst.ifBlock() != null) {
                flowGraph.add(currentBlock);
                Block next = new Block();
                if( empty ) currentBlock.addInstruction( inst );
                else next.addInstruction( inst );
                currentBlock = next;
            } else if (inst.callSomething() != null && inst.callSomething().callFunction() != null) {
                String name = Function.getVirtualName(inst.callSomething());
                if (Translator.program.getDefinedFunctions().containsKey(name)) {
                    flowGraph.add(currentBlock);
                    Block next = new Block();
                    if( empty ) currentBlock.addInstruction( inst );
                    else next.addInstruction( inst );
                    currentBlock = next;
                } else {
                    currentBlock.addInstruction(inst);
                }
            } else {
                currentBlock.addInstruction(inst);
            }
        }

        if (!currentBlock.getInstructions().isEmpty())
            flowGraph.add(currentBlock);
    }

    public void printFlowGraph() {
        int index = 0;
        for( Block block : flowGraph ) {
            System.out.println( "Block#" + (index++) );
            block.print();
        }
    }



    @Override
    public int compareTo(Function o) {
        return id.compareTo(o.getId());
    }

    public static String getVirtualName (CPPParser.FunctionContext ctx) {
        StringBuilder name = new StringBuilder(ctx.id().getText());
//        if( ctx.parameters() != null ) {
//            List<CPPParser.DatatypeContext> datatype = ctx.parameters().datatype();
//            datatype.forEach(currentType -> {
//                name.append("-" + currentType.getText());
//            });
//        }

        if (ctx.parameters() != null) {
            name.append("-" + ctx.parameters().datatype().size());
        }

        return name.toString();
    }

    public static String getVirtualName (CPPParser.CallSomethingContext ctx) {
        StringBuilder name = new StringBuilder(ctx.id().getText());

        if (ctx.callFunction().functionArguments() != null) {
            name.append("-" + ctx.callFunction().functionArguments().expression().size());
        }

        return name.toString();
    }
}
