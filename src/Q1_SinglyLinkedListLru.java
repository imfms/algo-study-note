/**
 * 单向链表Lru
 *
 * @author imf_m
 * @date 2019/5/15
 */
class Q1_SinglyLinkedListLru {

    public static void main(String[] args) {
        final int maxCacheNum = 4;
        Lru<Integer> lru = new Lru<>(maxCacheNum);

        for (int i : new int[]{1, 1, 2, 3, 4, 1, 5, 2, 2}) {
            lru.getValue(i);
        }

    }

    static class Node<T> {
        Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }

        T data;
        Node<T> next;
    }

    static class Lru<T> {

        private final int maxCacheNum;

        private Node<T> cacheLinkedListFirstNode;

        public Lru(int maxCacheNum) {
            this.maxCacheNum = maxCacheNum;
        }

        T getValue(T key) {

            Node<T> first = cacheLinkedListFirstNode;
            Node<T> second;

            while (true) {

                if (first == null) {
                    return getDataFromDbAndAddCache(key);
                }

                if (first.data.equals(key)) {
                    System.out.println("getDataFromCache(" + key + ")");
                    return first.data;
                }

                second = first.next;

                if (second == null) {
                    return getDataFromDbAndAddCache(key);
                }

                if (second.data.equals(key)) {
                    System.out.println("getDataFromCache(" + key + ")");
                    first.next = second.next;
                    second.next = cacheLinkedListFirstNode;
                    cacheLinkedListFirstNode = second;
                    return second.data;
                }

                first = first.next;
            }

        }

        private T getDataFromDbAndAddCache(T data) {
            T dbData = getValueFromDb(data);
            addToLruFirst(dbData);
            return dbData;
        }

        private void addToLruFirst(T data) {
            Node<T> node = this.cacheLinkedListFirstNode;
            for (int i = 1; node != null && i < maxCacheNum - 1; i++) {
                node = node.next;
            }
            if (node != null) {
                node.next = null;
            }

            cacheLinkedListFirstNode = new Node<>(data, cacheLinkedListFirstNode);
        }

        private T getValueFromDb(T data) {
            System.out.println("getDataFromDb(" + data + ")");
            return data;
        }
    }

}
