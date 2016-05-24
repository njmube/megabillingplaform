(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('File_stateDeleteController',File_stateDeleteController);

    File_stateDeleteController.$inject = ['$uibModalInstance', 'entity', 'File_state'];

    function File_stateDeleteController($uibModalInstance, entity, File_state) {
        var vm = this;
        vm.file_state = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            File_state.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
