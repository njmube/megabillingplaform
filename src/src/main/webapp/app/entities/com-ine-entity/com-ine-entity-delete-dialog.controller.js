(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_ine_entityDeleteController',Com_ine_entityDeleteController);

    Com_ine_entityDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_ine_entity'];

    function Com_ine_entityDeleteController($uibModalInstance, entity, Com_ine_entity) {
        var vm = this;
        vm.com_ine_entity = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_ine_entity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
