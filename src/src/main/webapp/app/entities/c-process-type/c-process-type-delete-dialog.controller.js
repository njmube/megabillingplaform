(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_process_typeDeleteController',C_process_typeDeleteController);

    C_process_typeDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_process_type'];

    function C_process_typeDeleteController($uibModalInstance, entity, C_process_type) {
        var vm = this;
        vm.c_process_type = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_process_type.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
