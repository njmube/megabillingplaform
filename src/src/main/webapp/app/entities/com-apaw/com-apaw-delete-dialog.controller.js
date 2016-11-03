(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_apawDeleteController',Com_apawDeleteController);

    Com_apawDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_apaw'];

    function Com_apawDeleteController($uibModalInstance, entity, Com_apaw) {
        var vm = this;
        vm.com_apaw = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_apaw.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
