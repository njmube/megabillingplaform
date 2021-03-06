(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Payment_methodDetailController', Payment_methodDetailController);

    Payment_methodDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Payment_method'];

    function Payment_methodDetailController($scope, $rootScope, $stateParams, entity, Payment_method) {
        var vm = this;
        vm.payment_method = entity;
        vm.load = function (id) {
            Payment_method.get({id: id}, function(result) {
                vm.payment_method = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:payment_methodUpdate', function(event, result) {
            vm.payment_method = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
