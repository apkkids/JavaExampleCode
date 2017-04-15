package com.dumaisoft.zhihu;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/1/7
 * Create Time: 12:40
 * Description:
 */
class GameObject {
    public int i;
    public int j;
    public String name;

    public GameObject(int i, int j, String name) {
        this.i = i;
        this.j = j;
        this.name = name;
    }
}

public class GameData {
    public static void main(String[] args) {
        GameObject go1 = new GameObject(1, 2, "go1");
        GameObject go2 = new GameObject(3, 4, "go2");
        List<GameObject> gos = saveGameObject2List(go1, go2);
        useGameObject(gos);
    }

    private static List<GameObject> saveGameObject2List(GameObject... gos) {
        List<GameObject> gameObjects = new ArrayList<>();
        for (GameObject go : gos) {
            gameObjects.add(go);
        }
        return gameObjects;
    }

    private static void useGameObject(List<GameObject> gos) {
        for (GameObject go : gos) {
            System.out.println("GameObject: i=" + go.i + ",j=" + go.j + ",name=" + go.name);
        }
    }
}
