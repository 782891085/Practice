package com.ekemp.practice.reflection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ekemp.practice.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 15-11-23.
 * 反射
 * 1,首先拿到Class<?> clazz对象
 * 有三种办法
 * ①通过对象获取getClassByObject()。
 * ②通过类名获取getClassByClassName()。
 * ③通过类名.class获取。例如：Persion.class;
 */
public class ReflectionTestActivity extends AppCompatActivity {
    @Bind(R.id.btn_1)
    Button btn_1;
    @Bind(R.id.btn_2)
    Button btn_2;
    @Bind(R.id.btn_3)
    Button btn_3;
    @Bind(R.id.btn_4)
    Button btn_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflection_test);
        ButterKnife.bind(this);
        setUpViews();
        setListeners();
    }

    private void setUpViews() {
    }

    private void setListeners() {

    }

    @OnClick(R.id.btn_1)
    public void btn_1Click() {
        showToast(getClassNameByClassObject(getClassByObject(new Persion())));
    }

    @OnClick(R.id.btn_2)
    public void btn_2Click() {
        //一般尽量采用这种形式
        showToast(getClassByClassName("com.ekemp.practice.reflection.Persion").getName());
        //showToast(Persion.class.getName());
        //showToast(getClassNameByClassObject(getClassByObject(new Persion())));
    }

    @OnClick(R.id.btn_3)
    public void btn_3Click() {
        Persion persion = null;
        Class<?> clazz = getClassByClassName("com.ekemp.practice.reflection.Persion");
        //Class<?> clazz=Persion.class;
        try {
            persion = (Persion) clazz.newInstance();
            persion.setName("zjx");
            showToast(persion.getName());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_4)
    public void btn_4Click() {
        try {
            Persion persion = (Persion) getConstructors(getClassByClassName("com.ekemp.practice.reflection.Persion"))[0].newInstance();
            showToast(persion.toString());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    //获取所有的构造器
    private Constructor<?>[] getConstructors(Class<?> clazz) {
        return clazz.getConstructors();
    }

    // 获取所有属性
    private Field[] getField(Class<?> clazz) {
        return clazz.getFields();
    }

    //获取属性权限修饰符
    private String getModifiers(Field field) {
        return Modifier.toString(field.getModifiers());
    }

    //获取属性名称
    private String getFieldName(Field field) {
        return field.getName();
    }

    //获取属性类型名称
    private String getFieldType(Field field) {
        return field.getType().getName();
    }

    //获取方法
    private Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterType) {
        try {
            return clazz.getMethod(methodName, parameterType);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    //使用无参方法
    private void doMethod(Class<?> clazz, String methodName) {
        try {
            getMethod(clazz, methodName).invoke(clazz.newInstance());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    //通过Class对象获取包名和类名
    private String getClassNameByClassObject(Class<?> clazz) {
        return clazz.getName();
    }

    //通过对象获取Class对象
    private Class<?> getClassByObject(Object obj) {
        return obj.getClass();
    }

    //通过类名获取Class对象
    private Class<?> getClassByClassName(String className) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
