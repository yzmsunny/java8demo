import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String ip = "58.248.229.154";
        String[] split = ip.split(",");
        if (Objects.nonNull(split) && split.length > 1) {
            ip = split[0];
        }
        System.out.println(ip);
    }

    private static void instant() {
        Instant now = Instant.now();
        long l = now.toEpochMilli();
    }

    private static void testCome() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("1111" + Thread.currentThread().getName());
            sleep(13000);
            String name = Thread.currentThread().getName();
            System.out.println(name + "inner end");
            return name;
        });
    }


    private static void testCompletaleFutrue() throws ExecutionException, InterruptedException {

        System.out.println("start->" + Thread.currentThread().getName());
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("1111" + Thread.currentThread().getName());
            sleep(13000);
            String name = Thread.currentThread().getName();
            System.out.println(name + "inner end");
            return name;
        });
        CompletableFuture<String> future1 = future.thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println("22222-" + Thread.currentThread().getName());
            sleep(4000);
            return "2222，";
        }), (f1, f2) -> f1 + "" + f2);
        sleep(1000);
        System.out.println("end->" + Thread.currentThread().getName() + "|||||=");
        System.out.println("_________" + future1.get());
        Thread.currentThread().join();
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

