(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Public_notaries_federal_entityDeleteController',Public_notaries_federal_entityDeleteController);

    Public_notaries_federal_entityDeleteController.$inject = ['$uibModalInstance', 'entity', 'Public_notaries_federal_entity'];

    function Public_notaries_federal_entityDeleteController($uibModalInstance, entity, Public_notaries_federal_entity) {
        var vm = this;
        vm.public_notaries_federal_entity = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Public_notaries_federal_entity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
