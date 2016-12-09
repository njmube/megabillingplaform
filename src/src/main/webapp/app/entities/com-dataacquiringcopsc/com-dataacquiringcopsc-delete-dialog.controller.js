(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_dataacquiringcopscDeleteController',Com_dataacquiringcopscDeleteController);

    Com_dataacquiringcopscDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_dataacquiringcopsc'];

    function Com_dataacquiringcopscDeleteController($uibModalInstance, entity, Com_dataacquiringcopsc) {
        var vm = this;
        vm.com_dataacquiringcopsc = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_dataacquiringcopsc.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
