(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_spei_thirdDetailController', Freecom_spei_thirdDetailController);

    Freecom_spei_thirdDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_spei_third', 'Freecom_spei', 'Freecom_payer', 'Freecom_beneficiary'];

    function Freecom_spei_thirdDetailController($scope, $rootScope, $stateParams, entity, Freecom_spei_third, Freecom_spei, Freecom_payer, Freecom_beneficiary) {
        var vm = this;

        vm.freecom_spei_third = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_spei_thirdUpdate', function(event, result) {
            vm.freecom_spei_third = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
