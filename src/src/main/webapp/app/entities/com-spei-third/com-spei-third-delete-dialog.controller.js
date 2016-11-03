(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_spei_thirdDeleteController',Com_spei_thirdDeleteController);

    Com_spei_thirdDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_spei_third'];

    function Com_spei_thirdDeleteController($uibModalInstance, entity, Com_spei_third) {
        var vm = this;
        vm.com_spei_third = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_spei_third.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
