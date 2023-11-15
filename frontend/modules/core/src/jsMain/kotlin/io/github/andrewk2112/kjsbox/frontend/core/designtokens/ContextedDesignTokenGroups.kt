package io.github.andrewk2112.kjsbox.frontend.core.designtokens

/**
 * Declares a group of context-based design tokens.
 */
abstract class ContextedDesignTokenGroup<C> {

    /**
     * Helper method to make design token declarations shorter.
     */
    protected fun <T> get(declaration: C.() -> T): (C) -> T = declaration

}

/**
 * Context-based sizes related to fonts.
 */
abstract class ContextedFontSizes<C> : ContextedDesignTokenGroup<C>()

/**
 * Context-based opacity values.
 */
abstract class ContextedOpacities<C> : ContextedDesignTokenGroup<C>()

/**
 * Context-based color palette.
 */
abstract class ContextedPalette<C> : ContextedDesignTokenGroup<C>()

/**
 * Context-based corner and shadow radius values.
 */
abstract class ContextedRadii<C> : ContextedDesignTokenGroup<C>()

/**
 * Context-based various element sizes.
 */
abstract class ContextedSizes<C> : ContextedDesignTokenGroup<C>()

/**
 * Context-based margin, padding values.
 */
abstract class ContextedSpacing<C> : ContextedDesignTokenGroup<C>()

/**
 * Context-based time duration values.
 */
abstract class ContextedTime<C> : ContextedDesignTokenGroup<C>()

/**
 * Context-based transition timing configurations.
 */
abstract class ContextedTiming<C> : ContextedDesignTokenGroup<C>()
