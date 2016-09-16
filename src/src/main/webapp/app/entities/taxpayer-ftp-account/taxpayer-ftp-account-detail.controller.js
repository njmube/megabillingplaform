(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_ftp_accountDetailController', Taxpayer_ftp_accountDetailController);

    Taxpayer_ftp_accountDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Taxpayer_ftp_account', 'Taxpayer_account'];

    function Taxpayer_ftp_accountDetailController($scope, $rootScope, $stateParams, entity, Taxpayer_ftp_account, Taxpayer_account) {
        var vm = this;

        vm.taxpayer_ftp_account = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:taxpayer_ftp_accountUpdate', function(event, result) {
            vm.taxpayer_ftp_account = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
