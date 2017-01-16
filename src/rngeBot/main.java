package rngeBot;

import org.osbot.rs07.api.Mouse;
import org.osbot.rs07.api.Skills;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;
 
@ScriptManifest(author = "You", info = "My first script", name = "rangebot", version = 0, logo = "")
public class main extends Script {
	
	Player player = myPlayer();
	
	NPC monster = npcs.closest("Sand Crab"); //can change npc to anything else
	
	
	Item Lobster = inventory.getItem("Lobster");
	
	Mouse mouse = getMouse();
	
	Skills skill = getSkills();
 
    @Override
    public void onStart() {
        log("Let's get started!");
    }
    
    private enum State{
    	ATTACK, EAT, WAIT;
    }
    
    private State getState(){
    	if(!player.isUnderAttack() && (monster.isAttackable()) && !player.isMoving()){
    		return State.ATTACK; //attack the monster
    	}
    	if(player.getHealthPercent()<50){
    		return State.EAT;
    	}
    	return State.WAIT; //wait til finished with fight
    	
    }
    @Override
    public int onLoop() throws InterruptedException {
        switch(getState()){
        case ATTACK:
        	if(!player.isMoving() && !player.isUnderAttack() && monster.isAttackable()){
        		monster.interact("Attack");
        	}
        	break;
        case EAT:
        	if(inventory.contains("Lobster")){
        			Lobster.interact("Eat");
        		}
        	
        	//antiban
        case WAIT:
        	double rand = (Math.random()*3);
        	switch((int)rand){
        	case 0:
        		if(player.isUnderAttack()){
        			skill.hoverSkill(Skill.HITPOINTS);
        		}
        	
        	case 1:
        		if(player.isUnderAttack()){
            		double rand1 = (Math.random()*1000)+500;
            		mouse.moveRandomly((int) rand1);
            	}
        	case 2:
        		if(player.isUnderAttack()){
        			skill.hoverSkill(Skill.RANGED);
        		}
        		}
        	}
        	
        return random(200, 300);
        }
    	
   
 
    @Override
    public void onExit() {
        log("rangebot !");
    }
 
    @Override
    public void onPaint(Graphics2D g) {
 
    }
 
}