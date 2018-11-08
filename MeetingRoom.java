import java.net.MalformedURLException;
import java.util.*;

/**
* 类似meeting rooms，输⼊入是⼀一个int[][] meetings, int start, int end, 每个数都是时间，13：00 =》
1300， 9：30 =》 930
 1.看新的meeting 能不不能安排到meetings
ex: {[1300, 1500], [930, 1200],[830, 845]},
 新的meeting[820, 830], return true;
 [1450, 1500] return false;
 2. 类似merge interval，唯一的区别是输出，输出空闲的时间段，merge完后，再把两两个之间的空的输出就好，注意要加上0 - 第一个的start time
 *
 * meeting room 2变种，每个interval有，weight，overlap的部分weight相加*/

public class MeetingRoom {
    /**1. can insert meeting*/
    public boolean haveMeetingRoom(int[][] meetings, int start, int end){
        List<int[]> meetinglist = new ArrayList<>();
        for(int[] meeting : meetings){
            meetinglist.add(meeting);
        }
        Collections.sort(meetinglist, (m1, m2) -> (m1[0] - m2[0]));
        if(end <= meetinglist.get(0)[0] || start >= meetinglist.get(meetinglist.size() - 1)[1] ){
            return true;
        }
        for(int i = 0; i < meetinglist.size() - 1; i++){
            if(start >= meetinglist.get(i)[1] && end <= meetinglist.get(i + 1)[0]){
                return true;
            }
        }
        return false;
    }
    /**2. merge interval*/
   // LC答案
    class Interval{
        int start;
        int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    /**
     * insert一个new interval，并merge*/
    public List<Interval> insertAndMerge(List<Interval> intervals, Interval newInterval) {
        List<Interval> result = new LinkedList<>();
        int i = 0;
        // add all the intervals ending before newInterval starts
        while (i < intervals.size() && intervals.get(i).end < newInterval.start)
            result.add(intervals.get(i++));
        // merge all overlapping intervals to one considering newInterval
        while (i < intervals.size() && intervals.get(i).start <= newInterval.end) {
            newInterval = new Interval( // we could mutate newInterval here also
                    Math.min(newInterval.start, intervals.get(i).start),
                    Math.max(newInterval.end, intervals.get(i).end));
            i++;
        }
        result.add(newInterval); // add the union of intervals we got
        // add all the rest
        while (i < intervals.size()) result.add(intervals.get(i++));
        return result;
    }

/**    一堆intervals，merge 比如[1, 4][2, 5] -> [1, 5]
*/
    public List<Interval> merge(List<Interval> intervals) {
        if (intervals.size() <= 1) return intervals;
        Collections.sort(intervals, (i1, i2) -> (i1.start - i2.start));

        List<Interval> result = new LinkedList<>();
        int start = intervals.get(0).start;
        int end = intervals.get(0).end;

        for (Interval interval : intervals) {
            if (interval.start <= end) // Overlapping intervals, move the end if needed
                end = Math.max(end, interval.end);
            else {                     // Disjoint intervals, add the previous one and reset bounds
                result.add(new Interval(start, end));
                start = interval.start;
                end = interval.end;
            }
        }

        result.add(new Interval(start, end));
        return result;
    }

    /**
     * 对于一个merge好的intervals，输出空闲时间段*/
    public List<String> printFreeTimeSlot(Interval[] intervals, int start, int end) { //start, end 是起始时间范围
        List<String> res = new ArrayList<>();
        if (intervals == null || intervals.length == 0) {
            return res;
        }
        Collections.sort(intervals, (a, b) -> (a.start - b.start));
        int begin = start;//the beginTime of freeTime (end of last meeting)
        for (Interval i : intervals) {
            if (begin >= end) {//if the start of free time is out of range(exceeds end), break the loop
                break;
            }
            if (i.start > begin) {//only add time range to res when there is a diff(free time) between two times
                res.add(begin + "-" + Math.min(i.start, end));//if the i.start exceeds end, we pick end to be the boundary
            }
            begin = Math.max(begin, i.end);//update begintime
        }
        return res;
    }


    public static void main(String[] args){
        MeetingRoom m = new MeetingRoom();
        int[][] meetings = {{1300, 1500}, {930, 1200},{830, 845}};
        System.out.println(m.haveMeetingRoom(meetings, 820, 830));
        System.out.println(m.haveMeetingRoom(meetings, 1450, 1500));
        System.out.println(m.haveMeetingRoom(meetings, 850, 940));

    }
}
