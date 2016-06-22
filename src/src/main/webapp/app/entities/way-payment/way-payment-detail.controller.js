(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Way_paymentDetailController', Way_paymentDetailController);

    Way_paymentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Way_payment'];

    function Way_paymentDetailController($scope, $rootScope, $stateParams, entity, Way_payment) {
        var vm = this;

        vm.way_payment = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:way_paymentUpdate', function(event, result) {
            vm.way_payment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
