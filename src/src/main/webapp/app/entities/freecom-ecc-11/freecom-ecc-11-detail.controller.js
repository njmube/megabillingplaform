(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_ecc11DetailController', Freecom_ecc11DetailController);

    Freecom_ecc11DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_ecc11', 'Free_cfdi'];

    function Freecom_ecc11DetailController($scope, $rootScope, $stateParams, entity, Freecom_ecc11, Free_cfdi) {
        var vm = this;

        vm.freecom_ecc11 = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_ecc11Update', function(event, result) {
            vm.freecom_ecc11 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
