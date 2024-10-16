package com.severinghams.homebrewsolitaire.core;
import android.opengl.GLES20;

import com.severinghams.homebrewsolitaire.GameRenderer;
import com.severinghams.homebrewsolitaire.core.enums.EnumSuit;

public class GLCard {
    private final int mProgram;
    public GLCard(EnumSuit suit, EnumRank rank) {}
        int vertexShader = GameRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(mProgram, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(mProgram);

    }
