(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('File_typeDeleteController',File_typeDeleteController);

    File_typeDeleteController.$inject = ['$uibModalInstance', 'entity', 'File_type'];

    function File_typeDeleteController($uibModalInstance, entity, File_type) {
        var vm = this;
        vm.file_type = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            File_type.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
