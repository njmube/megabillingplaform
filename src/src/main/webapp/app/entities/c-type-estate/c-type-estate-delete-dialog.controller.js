(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_type_estateDeleteController',C_type_estateDeleteController);

    C_type_estateDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_type_estate'];

    function C_type_estateDeleteController($uibModalInstance, entity, C_type_estate) {
        var vm = this;
        vm.c_type_estate = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_type_estate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
