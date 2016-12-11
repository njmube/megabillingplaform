(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_pn_federal_entityDeleteController',C_pn_federal_entityDeleteController);

    C_pn_federal_entityDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_pn_federal_entity'];

    function C_pn_federal_entityDeleteController($uibModalInstance, entity, C_pn_federal_entity) {
        var vm = this;
        vm.c_pn_federal_entity = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_pn_federal_entity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
