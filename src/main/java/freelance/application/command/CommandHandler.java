package freelance.application.command;

import java.util.ArrayList;
import java.util.List;

public interface CommandHandler {
   class HandlingContext{
      private final List<Runnable> onSuccessActions = new ArrayList<>();

      public void doOnSuccess(Runnable runnable){
         this.onSuccessActions.add(runnable);
      }

      public Iterable<Runnable> getOnSuccessActions(){
         return this.onSuccessActions;
      }
   }


   void handle(Command command, HandlingContext handlingContext);



}
