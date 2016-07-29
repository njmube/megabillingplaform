(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_federal_entityDeleteController',C_federal_entityDeleteController);

    C_federal_entityDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_federal_entity'];

    function C_federal_entityDeleteController($uibModalInstance, entity, C_federal_entity) {
        var vm = this;
        vm.c_federal_entity = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_federal_entity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
