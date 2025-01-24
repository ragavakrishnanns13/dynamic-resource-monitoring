import java.util.Stack;

public class ResourceMinMaxStack {
    private final Stack<ResourceUsage> stack; // Main stack to hold resource usage
    private final Stack<ResourceUsage> minStack; // Stack to track minimum usage
    private final Stack<ResourceUsage> maxStack; // Stack to track maximum usage

    // Inner class to represent resource usage
    static class ResourceUsage {
        int cpuUsage; // CPU usage percentage
        int memoryUsage; // Memory usage in MB

        public ResourceUsage(int cpuUsage, int memoryUsage) {
            this.cpuUsage = cpuUsage;
            this.memoryUsage = memoryUsage;
        }

        @Override
        public String toString() {
            return "{CPU: " + cpuUsage + "%, Memory: " + memoryUsage + " MB}";
        }
    }

    public ResourceMinMaxStack() {
        this.stack = new Stack<>();
        this.minStack = new Stack<>();
        this.maxStack = new Stack<>();
    }

    // Push a new resource usage update
    public void push(int cpuUsage, int memoryUsage) {
        ResourceUsage newUsage = new ResourceUsage(cpuUsage, memoryUsage);
        stack.push(newUsage);

        // Update the min stack
        if (minStack.isEmpty()) {
            minStack.push(newUsage);
        } else {
            ResourceUsage currentMin = minStack.peek();
            minStack.push(new ResourceUsage(
                Math.min(currentMin.cpuUsage, newUsage.cpuUsage),
                Math.min(currentMin.memoryUsage, newUsage.memoryUsage)
            ));
        }

        // Update the max stack
        if (maxStack.isEmpty()) {
            maxStack.push(newUsage);
        } else {
            ResourceUsage currentMax = maxStack.peek();
            maxStack.push(new ResourceUsage(
                Math.max(currentMax.cpuUsage, newUsage.cpuUsage),
                Math.max(currentMax.memoryUsage, newUsage.memoryUsage)
            ));
        }
    }

    // Pop the latest resource usage update
    public ResourceUsage pop() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        minStack.pop();
        maxStack.pop();
        return stack.pop();
    }

    // Get the minimum resource usage in the stack
    public ResourceUsage getMin() {
        if (minStack.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return minStack.peek();
    }

    // Get the maximum resource usage in the stack
    public ResourceUsage getMax() {
        if (maxStack.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return maxStack.peek();
    }

    // Get the current size of the stack
    public int size() {
        return stack.size();
    }

    // Check if the stack is empty
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        ResourceMinMaxStack resourceStack = new ResourceMinMaxStack();

        // Simulate resource usage updates
        resourceStack.push(50, 1024); // 50% CPU, 1024 MB memory
        resourceStack.push(70, 2048); // 70% CPU, 2048 MB memory
        resourceStack.push(40, 512);  // 40% CPU, 512 MB memory
        resourceStack.push(90, 4096); // 90% CPU, 4096 MB memory

        System.out.println("Current Resources: " + resourceStack.stack);
        System.out.println("Min Resource Usage: " + resourceStack.getMin()); // {CPU: 40%, Memory: 512 MB}
        System.out.println("Max Resource Usage: " + resourceStack.getMax()); // {CPU: 90%, Memory: 4096 MB}

        // Pop an update
        resourceStack.pop();
        System.out.println("After Pop:");
        System.out.println("Min Resource Usage: " + resourceStack.getMin()); // {CPU: 40%, Memory: 512 MB}
        System.out.println("Max Resource Usage: " + resourceStack.getMax()); // {CPU: 70%, Memory: 2048 MB}
    }
}
