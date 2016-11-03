(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_pficDeleteController',Com_pficDeleteController);

    Com_pficDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_pfic'];

    function Com_pficDeleteController($uibModalInstance, entity, Com_pfic) {
        var vm = this;
        vm.com_pfic = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_pfic.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
