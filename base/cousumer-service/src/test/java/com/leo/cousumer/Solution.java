package com.leo.cousumer;

import java.util.HashMap;
import java.util.Map;

/**
 * ceshi
 *
 * @author LIULU_LEO
 * Date 2020/5/31
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode listNode = new ListNode(0);
        ListNode ln1 = l1;
        ListNode ln2 = l2;
        ListNode listNode1 = listNode;
        int temp = 0;
        while(ln1!=null || ln2!=null ){
            int x = ln1!=null ? ln1.val : 0;
            int y = ln2!=null ? ln2.val : 0;
            int sum = x + y + temp;
            temp = sum/10;
            listNode1.next= new ListNode(sum%10);
            listNode1 = listNode1.next;
            if(ln1!=null){
                ln1 = ln1.next;
            }
            if(ln2!=null){
                ln2 = ln2.next;
            }

        }
        if(temp>0){
            listNode1.next =new ListNode(temp);
        }

        return listNode.next;
    }

    public static  class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        ListNode l1 = new ListNode(1);
        l1.next=new ListNode(3);
        ListNode l2 = new ListNode(2);
        l1.next=new ListNode(4);
        l1.next.next=new ListNode(6);
        solution.addTwoNumbers( l1,  l2);
    }
}


