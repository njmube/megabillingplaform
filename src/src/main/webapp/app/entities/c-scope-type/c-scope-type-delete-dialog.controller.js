(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_scope_typeDeleteController',C_scope_typeDeleteController);

    C_scope_typeDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_scope_type'];

    function C_scope_typeDeleteController($uibModalInstance, entity, C_scope_type) {
        var vm = this;
        vm.c_scope_type = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_scope_type.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
