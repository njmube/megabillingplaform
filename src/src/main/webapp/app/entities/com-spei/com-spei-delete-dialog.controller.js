(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_speiDeleteController',Com_speiDeleteController);

    Com_speiDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_spei'];

    function Com_speiDeleteController($uibModalInstance, entity, Com_spei) {
        var vm = this;
        vm.com_spei = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_spei.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
