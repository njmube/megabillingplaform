(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_request_confirmDetailController', Taxpayer_request_confirmDetailController);

    Taxpayer_request_confirmDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Taxpayer_request_confirm'];

    function Taxpayer_request_confirmDetailController($scope, $rootScope, $stateParams, entity, Taxpayer_request_confirm) {
        var vm = this;

        vm.taxpayer_request_confirm = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:taxpayer_request_confirmUpdate', function(event, result) {
            vm.taxpayer_request_confirm = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
