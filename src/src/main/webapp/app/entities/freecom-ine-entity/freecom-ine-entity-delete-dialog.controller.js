(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_ine_entityDeleteController',Freecom_ine_entityDeleteController);

    Freecom_ine_entityDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_ine_entity'];

    function Freecom_ine_entityDeleteController($uibModalInstance, entity, Freecom_ine_entity) {
        var vm = this;
        vm.freecom_ine_entity = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_ine_entity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
