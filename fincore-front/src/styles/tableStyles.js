import colors from "./colors";

/**
 * Reusable table building blocks. Visual styling for the table
 * itself continues to come from the "fin-table" CSS class in
 * DisplayView.css; these objects cover the per-cell inline tweaks
 * that were duplicated across every report/list page.
 */
const tableStyles = {
    wrapper: {
        overflowX: "auto",
    },

    table: {
        minWidth: "1100px",
    },

    idCell: {
        color: colors.brandBlue,
        fontWeight: "800",
    },

    avatarCell: {
        display: "flex",
        alignItems: "center",
        gap: "12px",
    },

    avatarCircle: {
        width: "38px",
        height: "38px",
        flexShrink: 0,
        borderRadius: "50%",
        background: colors.brandBlueSurface,
        color: colors.brandBlue,
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        fontWeight: "700",
        fontSize: "13px",
    },

    muted: {
        color: colors.textFaintAlt,
        fontSize: "13px",
    },

    actionsCell: {
        display: "flex",
        gap: "8px",
        alignItems: "center",
    },
};

export default tableStyles;
