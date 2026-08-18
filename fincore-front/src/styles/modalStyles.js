/**
 * Style hooks for ConfirmDialog. Visual chrome (overlay, box,
 * icon, title, message) is provided by the existing "modal-*"
 * classes in DisplayView.css so appearance is unchanged; this
 * file only covers the two-button action row a confirmation
 * dialog needs on top of the plain info Modal.
 */
const modalStyles = {
    actions: {
        display: "flex",
        gap: "10px",
        justifyContent: "center",
    },
};

export default modalStyles;
